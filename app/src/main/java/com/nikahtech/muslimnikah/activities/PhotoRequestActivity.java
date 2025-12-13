package com.nikahtech.muslimnikah.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.PhotoRequestAdapter;
import com.nikahtech.muslimnikah.databinding.ActivityPhotoRequestBinding;
import com.nikahtech.muslimnikah.enums.PhotoRequestType;
import com.nikahtech.muslimnikah.models.PhotoRequest;
import com.nikahtech.muslimnikah.utils.RecyclerPaddingDecorator;
import com.nikahtech.muslimnikah.utils.UIUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PhotoRequestActivity extends AppCompatActivity {

    ActivityPhotoRequestBinding binding;
    PhotoRequestAdapter adapter;
    List<PhotoRequest> requestList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhotoRequestBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                requestList.add(new PhotoRequest("", "", "", "", Instant.now(), PhotoRequestType.SENT));
            } else {
                requestList.add(new PhotoRequest("", "", "", "", Instant.now(), PhotoRequestType.RECEIVED));
            }
        }

        adapter = new PhotoRequestAdapter(this, requestList);
        binding.photoReqRc.setAdapter(adapter);
        binding.photoReqRc.setLayoutManager(new LinearLayoutManager(this));

        binding.photoReqRc.addItemDecoration(new RecyclerPaddingDecorator(
                UIUtil.dp(24),
                UIUtil.dp(24),
                UIUtil.dp(16),
                RecyclerView.VERTICAL
        ));

    }
}