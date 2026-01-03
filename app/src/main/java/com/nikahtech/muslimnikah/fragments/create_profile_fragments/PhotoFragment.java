package com.nikahtech.muslimnikah.fragments.create_profile_fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikahtech.muslimnikah.Backend.Api.ImageUploadApi;
import com.nikahtech.muslimnikah.Backend.ApiCaller;
import com.nikahtech.muslimnikah.Backend.ApiResponse;
import com.nikahtech.muslimnikah.Backend.RetrofitClient;
import com.nikahtech.muslimnikah.databinding.FragmentPhotoBinding;
import com.nikahtech.muslimnikah.utils.FullProgressBarUtil;
import com.nikahtech.muslimnikah.utils.ImageUtil;
import com.nikahtech.muslimnikah.utils.PopupDialogUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class PhotoFragment extends BaseStepFragment {

    private FragmentPhotoBinding binding;
    private FullProgressBarUtil progress;

    private ImageUploadApi secureApi;
    private ImageUploadApi publicApi;

    private static final String R2_SUBDOMAIN =
            "01d8fe89d3a24da88d6d520c5edcc235";

    private ActivityResultLauncher<Intent> picker;
    private ActivityResultLauncher<Intent> cropper;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentPhotoBinding.inflate(inflater, container, false);
        progress = new FullProgressBarUtil(requireContext());

        secureApi = RetrofitClient.createServiceWithAuth(
                ImageUploadApi.class, requireContext());
        publicApi = RetrofitClient.createService(ImageUploadApi.class);

        initPickers();
        initUi();

        return binding.getRoot();
    }

    private void initUi() {
        binding.profilePhotoImg.setOnClickListener(v -> openPicker());

        binding.nextBtn.setOnClickListener(v -> {
            if (profile().getProfilePic() == null) {
                PopupDialogUtil.show(
                        requireContext(),
                        PopupDialogUtil.Type.ERROR,
                        "Photo Required",
                        "Please upload a profile photo.",
                        "OK",
                        null
                );
                return;
            }
            stepNavigator.nextStep();
        });
    }

    // --------------------------------------------------
    // Image Flow
    // --------------------------------------------------

    private void initPickers() {

        cropper = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                res -> {
                    if (res.getResultCode() == Activity.RESULT_OK) {
                        Uri uri = UCrop.getOutput(res.getData());
                        binding.profilePhotoImg.setImageURI(uri);
                        if (uri != null) upload(uri);
                    }
                }
        );

        picker = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                res -> {
                    if (res.getResultCode() == Activity.RESULT_OK && res.getData() != null) {
                        Uri src = res.getData().getData();
                        Uri dst = Uri.fromFile(
                                new File(requireContext().getCacheDir(),
                                        "crop_" + System.currentTimeMillis() + ".jpg")
                        );

                        cropper.launch(
                                UCrop.of(src, dst)
                                        .withAspectRatio(1, 1)
                                        .withMaxResultSize(1080, 1080)
                                        .getIntent(requireContext())
                        );
                    }
                }
        );
    }

    private void openPicker() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        picker.launch(Intent.createChooser(i, "Select Photo"));
    }

    private void upload(Uri uri) {
        progress.show();

        String fileName = host().getUserId() + "_" + System.currentTimeMillis() + ".webp";

        ApiCaller.call(
                secureApi.getPresignedUrl(fileName),
                new ApiCaller.ApiCallback<>() {
                    @Override
                    public void onSuccess(ApiResponse<String> res) {
                        sendToR2(res.getData(), uri, fileName);
                    }

                    @Override
                    public void onError(ApiResponse<String> err) {
                        fail();
                    }
                }
        );
    }

    private void sendToR2(String url, Uri uri, String fileName) {
        try {
            RequestBody body = ImageUtil.webp(requireContext(), uri);
            publicApi.uploadImage(url, body).enqueue(new Callback<>() {
                @Override
                public void onResponse(
                        @NonNull Call<ResponseBody> c,
                        @NonNull Response<ResponseBody> r
                ) {
                    progress.dismiss();
                    if (!r.isSuccessful()) {
                        fail();
                        return;
                    }
                    profile().setProfilePic(
                            "https://pub-" + R2_SUBDOMAIN +
                                    ".r2.dev/user-images/" + fileName
                    );
                }

                @Override
                public void onFailure(
                        @NonNull Call<ResponseBody> c,
                        @NonNull Throwable t
                ) {
                    progress.dismiss();
                    fail();
                }
            });
        } catch (Exception e) {
            fail();
        }
    }

    private void fail() {
        progress.dismiss();
        PopupDialogUtil.show(
                requireContext(),
                PopupDialogUtil.Type.ERROR,
                "Upload Failed",
                "Please try again.",
                "OK",
                null
        );
    }
}

