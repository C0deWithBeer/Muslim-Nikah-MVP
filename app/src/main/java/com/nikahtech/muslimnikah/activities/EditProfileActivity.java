package com.nikahtech.muslimnikah.activities;

import static com.nikahtech.muslimnikah.utils.UIUtil.dp;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.nikahtech.muslimnikah.Backend.Api.ImageUploadApi;
import com.nikahtech.muslimnikah.Backend.Api.ProfileApi;
import com.nikahtech.muslimnikah.Backend.ApiCaller;
import com.nikahtech.muslimnikah.Backend.ApiResponse;
import com.nikahtech.muslimnikah.Backend.RetrofitClient;
import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.Backend.enums.Gender;
import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.EditProfilePhotoAdapter;
import com.nikahtech.muslimnikah.databinding.ActivityEditProfileBinding;
import com.nikahtech.muslimnikah.keystores.LocalDBManager;
import com.nikahtech.muslimnikah.models.editprofile.ProfilePhotoItem;
import com.nikahtech.muslimnikah.utils.FullProgressBarUtil;
import com.nikahtech.muslimnikah.utils.ImageUtil;
import com.nikahtech.muslimnikah.utils.JSONUtil;
import com.nikahtech.muslimnikah.utils.PopupDialogUtil;
import com.nikahtech.muslimnikah.utils.ProfileOptionsManager;
import com.nikahtech.muslimnikah.utils.RecyclerPaddingDecorator;
import com.nikahtech.muslimnikah.utils.SingleOptionPicker;
import com.nikahtech.muslimnikah.utils.SysUtil;
import com.nikahtech.muslimnikah.utils.ToastUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;

    private ProfileDto profileDto;

    /* ---------------------------------------------
     * Photos
     * --------------------------------------------- */
    private final List<ProfilePhotoItem> photoItems = new ArrayList<>();
    private EditProfilePhotoAdapter photoAdapter;

    /* ---------------------------------------------
     * Section ↔ Tab Sync
     * --------------------------------------------- */
    private View[] sections;
    private boolean isTabClickScrolling = false;

    private static final int PHOTO_PROFILE = 0;
    private static final int PHOTO_GALLERY = 1;

    private int currentPhotoType = PHOTO_PROFILE;
    private int currentGalleryPosition = -1;

    private ImageUploadApi secureApi;
    private ImageUploadApi publicApi;
    private FullProgressBarUtil progress;

    private static final String R2_SUBDOMAIN =
            "01d8fe89d3a24da88d6d520c5edcc235";

    private ActivityResultLauncher<Intent> picker;
    private ActivityResultLauncher<Intent> cropper;

    private boolean hasChanges = false;


    /* ---------------------------------------------
     * Lifecycle
     * --------------------------------------------- */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        progress = new FullProgressBarUtil(this);

        secureApi = RetrofitClient.createServiceWithAuth(
                ImageUploadApi.class, this
        );
        publicApi = RetrofitClient.createService(ImageUploadApi.class);

        getOnBackPressedDispatcher().addCallback(
                this,
                new androidx.activity.OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        handleBackAction();
                    }
                }
        );

        applyWindowInsets();
        setupClickListeners();
        initSections();
        setupTabs();
        setupScrollSync();
        setupPhotos();
        loadLocalProfile();

        bindBasicData();
        bindContactData();
        bindEduWorkData();
        bindReligiousData();
        bindLocationData();
        bindFamilyData();

        initPhotoPickers();
        initPickers();
        initDatePicker();
        initEduWorkPickers();
        initLocationPickers();
        initFamilyPickers();
        initReligiousPickers();

        watchTextChanges(binding.fullNameEdt);
        watchTextChanges(binding.emailEdt);
        watchTextChanges(binding.phoneEdt);
        watchTextChanges(binding.cityEdt);
        watchTextChanges(binding.stateEdt);

        initUpdateButton();
    }

    // --------------------------------------------------
    // Load Profile
    // --------------------------------------------------

    private void loadLocalProfile() {
        profileDto = LocalDBManager.getProfile(this);

        if (profileDto == null) {
            finish(); // no profile → cannot edit
        }

        if (profileDto.getGallery() == null) {
            profileDto.setGallery(new ArrayList<>());
        }

    }

    // --------------------------------------------------
    // Bind Existing Values
    // --------------------------------------------------

    private void bindBasicData() {

        binding.profileCreatedByEdt.setText(profileDto.getProfileCreatedBy());
        binding.fullNameEdt.setText(profileDto.getName());

        if (profileDto.getGender() != null) {
            binding.genderEdt.setText(capitalize(profileDto.getGender().name()));
        }

        if (profileDto.getDateOfBirth() != null) {
            binding.dobEdt.setText(profileDto.getDateOfBirth().toString());
        }

        if (profileDto.getHeight() != null && profileDto.getHeight() > 0) {
            binding.heightEdt.setText(formatHeight(profileDto.getHeight()));
        }

        binding.maritalStatusEdt.setText(profileDto.getMaritalStatus());
        binding.disabilityEdt.setText(profileDto.getDisabilityStatus());
    }

    private void bindContactData() {

        binding.emailEdt.setText(profileDto.getEmailAddress());
        binding.phoneEdt.setText(profileDto.getPhoneNumber());

    }

    private void bindEduWorkData() {
        binding.highestEducationEdt.setText(profileDto.getHighestEducation());
        binding.fieldOfStudyEdt.setText(profileDto.getFieldOfStudy());
        binding.employedInEdt.setText(profileDto.getEmploymentType());
        binding.occupationEdt.setText(profileDto.getOccupation());
        binding.annualIncomeEdt.setText(profileDto.getIncomeRange());
    }

    private void bindLocationData() {

        binding.cityEdt.setText(profileDto.getCity());
        binding.stateEdt.setText(profileDto.getState());
        binding.countryEdt.setText(profileDto.getCountry());
        binding.motherTongueEdt.setText(profileDto.getMotherTongue());
    }

    private void bindReligiousData() {

        binding.sectEdt.setText(profileDto.getSect());
        binding.casteEdt.setText(profileDto.getCaste());
        binding.namazFrequencyEdt.setText(profileDto.getPrayerHabit());
        binding.readQuranEdt.setText(profileDto.getQuranReading());
        binding.halalDietEdt.setText(profileDto.getHalalDiet());
        binding.beardHijabEdt.setText(profileDto.getBeardHijab());
        binding.religiousLevelEdt.setText(profileDto.getInvolvement());
    }


    private void bindFamilyData() {

        binding.familyStatusEdt.setText(profileDto.getFamilyStatus());
        binding.familyTypeEdt.setText(profileDto.getFamilyType());
        binding.fatherOccupationEdt.setText(profileDto.getFatherOccupation());
        binding.siblingsCountEdt.setText(profileDto.getSiblingsCount());
    }


    private String capitalize(String str) {
        // Check if string is null or empty to avoid errors
        if (str == null || str.isEmpty()) {
            return str;
        }

        // Capitalize the first letter and lowercase the rest
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private void initPhotoPickers() {

        cropper = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                res -> {
                    if (res.getResultCode() == RESULT_OK) {
                        Uri uri = UCrop.getOutput(res.getData());
                        if (uri != null) uploadPhoto(uri);
                    }
                }
        );

        picker = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                res -> {
                    if (res.getResultCode() == RESULT_OK && res.getData() != null) {

                        Uri src = res.getData().getData();
                        Uri dst = Uri.fromFile(
                                new File(getCacheDir(),
                                        "crop_" + System.currentTimeMillis() + ".jpg")
                        );

                        cropper.launch(
                                UCrop.of(src, dst)
                                        .withAspectRatio(1, 1)
                                        .withMaxResultSize(1080, 1080)
                                        .getIntent(this)
                        );
                    }
                }
        );
    }


    private void initPickers() {

        attachPicker(binding.profileCreatedByEdt,
                ProfileOptionsManager.profileCreatedBy(),
                "Profile Created By");

        attachPicker(binding.genderEdt,
                ProfileOptionsManager.gender(),
                "Gender");

        attachPicker(binding.heightEdt,
                ProfileOptionsManager.heightInFt(),
                "Height");

        attachPicker(binding.maritalStatusEdt,
                ProfileOptionsManager.maritalStatus(),
                "Marital Status");

        attachPicker(binding.disabilityEdt,
                ProfileOptionsManager.disabilityStatus(),
                "Disability");
    }

    private void initEduWorkPickers() {

        attachPicker(binding.highestEducationEdt,
                ProfileOptionsManager.highestEducation(),
                "Highest Education");

        attachPicker(binding.fieldOfStudyEdt,
                ProfileOptionsManager.fieldOfStudy(),
                "Field of Study");

        attachPicker(binding.employedInEdt,
                ProfileOptionsManager.employmentType(),
                "Employed In");

        attachPicker(binding.occupationEdt,
                ProfileOptionsManager.occupation(),
                "Occupation");

        attachPicker(binding.annualIncomeEdt,
                ProfileOptionsManager.annualIncome(),
                "Annual Income");
    }

    private void initLocationPickers() {

        attachPicker(
                binding.countryEdt,
                ProfileOptionsManager.countries(),
                "Country"
        );

        attachPicker(
                binding.motherTongueEdt,
                ProfileOptionsManager.languages(),
                "Mother Tongue"
        );
    }

    private void initFamilyPickers() {

        attachPicker(
                binding.familyStatusEdt,
                ProfileOptionsManager.familyStatus(),
                "Family Status"
        );

        attachPicker(
                binding.familyTypeEdt,
                ProfileOptionsManager.familyType(),
                "Family Type"
        );

        attachPicker(
                binding.fatherOccupationEdt,
                ProfileOptionsManager.fatherOccupation(),
                "Father's Occupation"
        );

        attachPicker(
                binding.siblingsCountEdt,
                ProfileOptionsManager.siblingCount(),
                "Sibling Count"
        );
    }

    private void initReligiousPickers() {

        attachPicker(
                binding.sectEdt,
                ProfileOptionsManager.sects(),
                "Sect"
        );

        attachPicker(
                binding.casteEdt,
                ProfileOptionsManager.castes(),
                "Caste"
        );

        attachPicker(
                binding.namazFrequencyEdt,
                ProfileOptionsManager.namazFrequencies(),
                "Prayer Habit"
        );

        attachPicker(
                binding.readQuranEdt,
                ProfileOptionsManager.readQuranOptions(),
                "Quran Reading"
        );

        attachPicker(
                binding.halalDietEdt,
                ProfileOptionsManager.halalDietOptions(),
                "Halal Diet"
        );

        attachPicker(
                binding.beardHijabEdt,
                ProfileOptionsManager.beardHijabOptions(),
                "Beard / Hijab Preferences"
        );

        attachPicker(
                binding.religiousLevelEdt,
                ProfileOptionsManager.religiousObservationLevels(),
                "Religious Involvement"
        );
    }


    private void attachPicker(
            TextInputEditText edt,
            List<String> options,
            String title
    ) {
        edt.setShowSoftInputOnFocus(false);

        View.OnClickListener open = v -> {
            new SingleOptionPicker(
                    EditProfileActivity.this,
                    options,
                    edt,
                    title,
                    edt.getText().toString(),
                    selected -> {
                        String newValue = selected.toString();
                        String oldValue = edt.getText() == null ? "" : edt.getText().toString();

                        if (!newValue.equals(oldValue)) {
                            hasChanges = true;
                        }

                        edt.setText(newValue);
                        edt.clearFocus();
                    }
            ).open();
        };

        edt.setOnClickListener(open);
        edt.setOnFocusChangeListener((v, f) -> {
            if (f) open.onClick(v);
        });
    }

    private void watchTextChanges(TextInputEditText edt) {
        edt.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                hasChanges = true;
            }
            @Override public void afterTextChanged(android.text.Editable s) {}
        });
    }


    // --------------------------------------------------
    // Date Picker
    // --------------------------------------------------

    private void initDatePicker() {

        binding.dobEdt.setOnClickListener(this::openDatePicker);
        binding.dobEdt.setOnFocusChangeListener((v, f) -> {
            if (f) openDatePicker(v);
        });
    }

    private void openDatePicker(View v) {

        SysUtil.hideKeyboard(v);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18);

        MaterialDatePicker<Long> picker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date of Birth")
                        .setTheme(R.style.ThemeOverlay_App_DatePicker)
                        .setSelection(cal.getTimeInMillis())
                        .setCalendarConstraints(
                                new CalendarConstraints.Builder()
                                        .setEnd(cal.getTimeInMillis())
                                        .build()
                        )
                        .build();

        picker.addOnPositiveButtonClickListener(ms -> {
            LocalDate date = Instant.ofEpochMilli(ms)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            binding.dobEdt.setText(date.toString());
            binding.dobEdt.clearFocus();
        });

        picker.show(getSupportFragmentManager(), "DOB_PICKER");
    }

    private void initUpdateButton() {

        binding.updateProfileBtn.setOnClickListener(v -> {

            if (!validateBasic()) return;
            if (!validateContact()) return;
            if (!validateEduWork()) return;
            if (!validateLocation()) return;
            if (!validateReligious()) return;

            persistPhotos();
            persistBasic();
            persistContact();
            persistEduWork();
            persistLocation();
            persistFamily();
            persistReligious();

            updateProfile();

        });
    }

    private boolean validateBasic() {

        clearErrors();

        if (isEmpty(binding.profileCreatedByEdt)) {
            binding.profileCreatedByInputLay.setError("Required");
            return false;
        }
        if (isEmpty(binding.genderEdt)) {
            binding.genderInputLay.setError("Required");
            return false;
        }
        if (isEmpty(binding.dobEdt)) {
            binding.dobInputLay.setError("Required");
            return false;
        }
        if (isEmpty(binding.heightEdt)) {
            binding.heightInputLay.setError("Required");
            return false;
        }
        if (isEmpty(binding.maritalStatusEdt)) {
            binding.maritalStatusInputLay.setError("Required");
            return false;
        }
        if (isEmpty(binding.disabilityEdt)) {
            binding.disabilityInputLay.setError("Required");
            return false;
        }

        return true;
    }

    private boolean validateContact() {

        binding.emailInputLay.setError(null);
        binding.phoneInputLay.setError(null);

//        if (isEmpty(binding.emailEdt)) {
//            binding.emailInputLay.setError("Required");
//            return false;
//        }

        if (isEmpty(binding.phoneEdt)) {
            binding.phoneInputLay.setError("Required");
            return false;
        }

        return true;
    }

    private boolean validateEduWork() {

        clearEduWorkErrors();

        if (isEmpty(binding.highestEducationEdt)) {
            binding.highestEducationInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.fieldOfStudyEdt)) {
            binding.fieldOfStudyInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.employedInEdt)) {
            binding.employedInInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.occupationEdt)) {
            binding.occupationInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.annualIncomeEdt)) {
            binding.annualIncomeInputLay.setError("Required");
            return false;
        }

        return true;
    }

    private boolean validateLocation() {

        clearLocationErrors();

        if (isEmpty(binding.cityEdt)) {
            binding.cityInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.stateEdt)) {
            binding.stateInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.countryEdt)) {
            binding.countryInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.motherTongueEdt)) {
            binding.motherTongueInputLay.setError("Required");
            return false;
        }

        return true;
    }

    private boolean validateReligious() {

        clearReligiousErrors();

        if (isEmpty(binding.sectEdt)) {
            binding.sectInputLay.setError("Required");
            return false;
        }

        if (isEmpty(binding.casteEdt)) {
            binding.casteInputLay.setError("Required");
            return false;
        }

        return true;
    }


    private void clearErrors() {
        binding.profileCreatedByInputLay.setError(null);
        binding.genderInputLay.setError(null);
        binding.dobInputLay.setError(null);
        binding.heightInputLay.setError(null);
        binding.maritalStatusInputLay.setError(null);
        binding.disabilityInputLay.setError(null);
    }

    private void clearEduWorkErrors() {
        binding.highestEducationInputLay.setError(null);
        binding.fieldOfStudyInputLay.setError(null);
        binding.employedInInputLay.setError(null);
        binding.occupationInputLay.setError(null);
        binding.annualIncomeInputLay.setError(null);
    }

    private void clearLocationErrors() {
        binding.cityInputLay.setError(null);
        binding.stateInputLay.setError(null);
        binding.countryInputLay.setError(null);
        binding.motherTongueInputLay.setError(null);
    }

    private void clearReligiousErrors() {
        binding.sectInputLay.setError(null);
        binding.casteInputLay.setError(null);
    }


    private boolean isEmpty(TextInputEditText edt) {
        return edt.getText() == null || edt.getText().toString().trim().isEmpty();
    }


    private void persistPhotos() {

        if (photoItems.isEmpty()) return;

        // Profile photo = index 0
        ProfilePhotoItem profileItem = photoItems.get(0);
        if (profileItem.getType() == ProfilePhotoItem.TYPE_PHOTO) {
            profileDto.setProfilePic(profileItem.getImageUrl());
        }

        // Gallery photos = index 1..n (ignore ADD button)
        List<String> gallery = new ArrayList<>();

        for (int i = 1; i < photoItems.size(); i++) {
            ProfilePhotoItem item = photoItems.get(i);
            if (item.getType() == ProfilePhotoItem.TYPE_PHOTO) {
                gallery.add(item.getImageUrl());
            }
        }

        profileDto.setGallery(gallery);
    }

    private void persistBasic() {

        profileDto.setProfileCreatedBy(value(binding.profileCreatedByEdt));
        profileDto.setName(value(binding.fullNameEdt));
        profileDto.setGender(parseGender(value(binding.genderEdt)));
        profileDto.setDateOfBirth(LocalDate.parse(
                value(binding.dobEdt),
                DateTimeFormatter.ISO_LOCAL_DATE
        ));
        profileDto.setHeight(parseHeightCm(value(binding.heightEdt)));
        profileDto.setMaritalStatus(value(binding.maritalStatusEdt));
        profileDto.setDisabilityStatus(value(binding.disabilityEdt));
    }

    private void persistContact() {
        profileDto.setEmailAddress(value(binding.emailEdt));
        profileDto.setPhoneNumber(value(binding.phoneEdt));
    }

    private void persistEduWork() {

        profileDto.setHighestEducation(value(binding.highestEducationEdt));
        profileDto.setFieldOfStudy(value(binding.fieldOfStudyEdt));
        profileDto.setEmploymentType(value(binding.employedInEdt));
        profileDto.setOccupation(value(binding.occupationEdt));
        profileDto.setIncomeRange(value(binding.annualIncomeEdt));
    }

    private void persistLocation() {
        profileDto.setCity(value(binding.cityEdt));
        profileDto.setState(value(binding.stateEdt));
        profileDto.setCountry(value(binding.countryEdt));
        profileDto.setMotherTongue(value(binding.motherTongueEdt));
    }

    private void persistFamily() {

        profileDto.setFamilyStatus(value(binding.familyStatusEdt));
        profileDto.setFamilyType(value(binding.familyTypeEdt));
        profileDto.setFatherOccupation(value(binding.fatherOccupationEdt));
        profileDto.setSiblingsCount(value(binding.siblingsCountEdt));
    }

    private void persistReligious() {

        profileDto.setSect(value(binding.sectEdt));
        profileDto.setCaste(value(binding.casteEdt));
        profileDto.setPrayerHabit(value(binding.namazFrequencyEdt));
        profileDto.setQuranReading(value(binding.readQuranEdt));
        profileDto.setHalalDiet(value(binding.halalDietEdt));
        profileDto.setBeardHijab(value(binding.beardHijabEdt));
        profileDto.setInvolvement(value(binding.religiousLevelEdt));
    }



    // --------------------------------------------------
    // API Call
    // --------------------------------------------------

    private void updateProfile() {

        ProfileApi api = RetrofitClient.createServiceWithAuth(ProfileApi.class, this);

        Call<ApiResponse<ProfileDto>> call =
                api.updateProfile(profileDto.getId(), profileDto);

        progress.show();

        ApiCaller.call(call, new ApiCaller.ApiCallback<>() {

            @Override
            public void onSuccess(ApiResponse<ProfileDto> response) {

                progress.dismiss();
                hasChanges = false;

                // Save updated profile locally
                LocalDBManager.getStore(EditProfileActivity.this)
                                .insertOrUpdate("profile", JSONUtil.toJson(response.getData()));

                ToastUtil.show(EditProfileActivity.this, "Profile Updated", ToastUtil.AppToastType.SUCCESS);
            }

            @Override
            public void onError(ApiResponse<ProfileDto> error) {
                progress.dismiss();
                ToastUtil.show(EditProfileActivity.this, "Failed to update", ToastUtil.AppToastType.ERROR);
            }
        });
    }

    private String value(TextInputEditText edt) {
        return edt.getText().toString().trim();
    }

    private static int parseHeightCm(String value) {
        try {
            String s = value.toLowerCase().replace(" ", "");
            int ft = Integer.parseInt(s.split("ft")[0]);
            int in = s.contains("in")
                    ? Integer.parseInt(s.split("ft")[1].replace("in", ""))
                    : 0;
            return (int) (ft * 30.48 + in * 2.54);
        } catch (Exception e) {
            return 0;
        }
    }

    private static String formatHeight(int cm) {
        int ft = (int) (cm / 30.48);
        int in = (int) ((cm - ft * 30.48) / 2.54);
        return ft + " ft " + in + " in";
    }

    private static Gender parseGender(String v) {
        return "male".equalsIgnoreCase(v)
                ? Gender.MALE
                : Gender.FEMALE;
    }

    private void setupClickListeners() {

        binding.backBtn.setOnClickListener(v -> handleBackAction());

        binding.viewProfileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfileActivity.this, ViewProfileActivity.class);
            startActivity(intent);
        });

    }

    private void handleBackAction() {

        if (!hasChanges) {
            finish();
            return;
        }

        showUnsavedChangesDialog();
    }

    private void showUnsavedChangesDialog() {

        View view = getLayoutInflater()
                .inflate(R.layout.dialog_unsaved_changes, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create();

        // Make background transparent so rounded drawable shows
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(
                    new android.graphics.drawable.ColorDrawable(
                            android.graphics.Color.TRANSPARENT
                    )
            );
        }

        view.findViewById(R.id.updateBtn).setOnClickListener(v -> {
            dialog.dismiss();
            binding.updateProfileBtn.performClick();
        });

        view.findViewById(R.id.discardBtn).setOnClickListener(v -> {
            hasChanges = false;
            dialog.dismiss();
            finish();
        });

        dialog.show();
    }



    /* ---------------------------------------------
     * Window Insets
     * --------------------------------------------- */
    private void applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                }
        );
    }

    /* ---------------------------------------------
     * Sections order MUST match Tab order
     * --------------------------------------------- */
    private void initSections() {
        sections = new View[]{
                binding.sectionPhotos,
                binding.sectionContact,
                binding.sectionBasic,
                binding.sectionEdu,
                binding.sectionLocation,
                binding.sectionReligious,
                binding.sectionFamily
        };
    }

    /* ---------------------------------------------
     * Tab → Scroll
     * --------------------------------------------- */
    private void setupTabs() {

        binding.tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        isTabClickScrolling = true;
                        scrollToSection(sections[tab.getPosition()]);
                    }

                    @Override public void onTabUnselected(TabLayout.Tab tab) {}
                    @Override public void onTabReselected(TabLayout.Tab tab) {}
                }
        );
    }

    private void scrollToSection(View target) {
        binding.nestedScrollView.post(() -> {
            binding.nestedScrollView.smoothScrollTo(
                    0,
                    target.getTop() - dp(8)
            );

            // Allow scroll listener again after animation
            binding.nestedScrollView.postDelayed(
                    () -> isTabClickScrolling = false,
                    300
            );
        });
    }

    /* ---------------------------------------------
     * Scroll → Tab
     * --------------------------------------------- */
    private void setupScrollSync() {

        binding.nestedScrollView.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener)
                        (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

                            if (isTabClickScrolling) return;

                            int currentIndex = getActiveSectionIndex(scrollY);

                            if (currentIndex != binding.tabLayout.getSelectedTabPosition()) {
                                TabLayout.Tab tab =
                                        binding.tabLayout.getTabAt(currentIndex);
                                if (tab != null) tab.select();
                            }
                        }
        );
    }

    private int getActiveSectionIndex(int scrollY) {

        int buffer = dp(16);

        for (int i = sections.length - 1; i >= 0; i--) {
            if (scrollY + buffer >= sections[i].getTop()) {
                return i;
            }
        }

        return 0;
    }

    /* ---------------------------------------------
     * Photos Recycler
     * --------------------------------------------- */
    private void setupPhotos() {

        List<String> galleryUrls = new ArrayList<>();

        if (LocalDBManager.getProfile(this).getGallery() != null) {
            galleryUrls.addAll(LocalDBManager.getProfile(this).getGallery());
        }

        galleryUrls.add(0, LocalDBManager.getProfile(this).getProfilePic());

        photoItems.clear();

        for (String url : galleryUrls) {
            photoItems.add(
                    new ProfilePhotoItem(url, ProfilePhotoItem.TYPE_PHOTO)
            );
        }

        if (photoItems.size() < 5) {
            photoItems.add(
                    new ProfilePhotoItem(null, ProfilePhotoItem.TYPE_ADD)
            );
        }

        photoAdapter = new EditProfilePhotoAdapter(
                this,
                photoItems,

                // ADD PHOTO
                () -> {
                    currentPhotoType = PHOTO_GALLERY;
                    currentGalleryPosition = photoItems.size() - 1;
                    openImagePicker();
                },

                // PROFILE PHOTO CLICK
                url -> {
                    currentPhotoType = PHOTO_PROFILE;
                    openImagePicker();
                },

                // GALLERY PHOTO CLICK
                (url, position) -> {
                    currentPhotoType = PHOTO_GALLERY;
                    currentGalleryPosition = position;
                    openImagePicker();
                }
        );


        binding.photosRecycler.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        RecyclerView.HORIZONTAL,
                        false
                )
        );

        binding.photosRecycler.setAdapter(photoAdapter);

        binding.photosRecycler.addItemDecoration(
                new RecyclerPaddingDecorator(
                        dp(16),
                        dp(32),
                        dp(24),
                        RecyclerView.HORIZONTAL
                )
        );
    }

    private void openImagePicker() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        picker.launch(Intent.createChooser(i, "Select Photo"));
    }

    private void uploadPhoto(Uri uri) {

        progress.show();

        String fileName =
                profileDto.getUserId() + "_" +
                        System.currentTimeMillis() + ".webp";

        ApiCaller.call(
                secureApi.getPresignedUrl(fileName),
                new ApiCaller.ApiCallback<>() {

                    @Override
                    public void onSuccess(ApiResponse<String> res) {
                        sendToR2(res.getData(), uri, fileName);
                    }

                    @Override
                    public void onError(ApiResponse<String> err) {
                        failUpload();
                    }
                }
        );
    }

    private void sendToR2(String url, Uri uri, String fileName) {

        try {
            RequestBody body = ImageUtil.webp(this, uri);

            publicApi.uploadImage(url, body).enqueue(new Callback<>() {

                @Override
                public void onResponse(
                        @NonNull Call<ResponseBody> call,
                        @NonNull Response<ResponseBody> res
                ) {
                    progress.dismiss();

                    if (!res.isSuccessful()) {
                        failUpload();
                        return;
                    }

                    String publicUrl =
                            "https://pub-" + R2_SUBDOMAIN +
                                    ".r2.dev/user-images/" + fileName;

                    applyUploadedPhoto(publicUrl);
                }

                @Override
                public void onFailure(
                        @NonNull Call<ResponseBody> call,
                        @NonNull Throwable t
                ) {
                    progress.dismiss();
                    failUpload();
                }
            });

        } catch (Exception e) {
            failUpload();
        }
    }

    private void applyUploadedPhoto(String url) {

        hasChanges = true;

        if (currentPhotoType == PHOTO_PROFILE) {

            // Update DTO
            profileDto.setProfilePic(url);

            // Update UI list
            photoItems.set(0,
                    new ProfilePhotoItem(url, ProfilePhotoItem.TYPE_PHOTO)
            );

        } else {

            // Replace or add gallery photo
            if (currentGalleryPosition < photoItems.size()
                    && photoItems.get(currentGalleryPosition).getType()
                    == ProfilePhotoItem.TYPE_PHOTO) {

                photoItems.set(
                        currentGalleryPosition,
                        new ProfilePhotoItem(url, ProfilePhotoItem.TYPE_PHOTO)
                );

            } else {

                photoItems.add(
                        photoItems.size() - 1,
                        new ProfilePhotoItem(url, ProfilePhotoItem.TYPE_PHOTO)
                );
            }

            // Limit to 5 total
            while (photoItems.size() > 5) {
                photoItems.remove(photoItems.size() - 1);
            }
        }

        photoAdapter.notifyDataSetChanged();

        // Persist locally immediately
        LocalDBManager.getStore(this)
                .insertOrUpdate("profile", JSONUtil.toJson(profileDto));
    }


    private void failUpload() {
        progress.dismiss();
        PopupDialogUtil.show(
                this,
                PopupDialogUtil.Type.ERROR,
                "Upload Failed",
                "Please try again.",
                "OK",
                null
        );
    }


}
