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
import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.VerticalProfilesAdapter;
import com.nikahtech.muslimnikah.databinding.ActivityListProfilesBinding;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.utils.RecyclerPaddingDecorator;
import com.nikahtech.muslimnikah.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

public class ListProfilesActivity extends AppCompatActivity {

    ActivityListProfilesBinding binding;
    VerticalProfilesAdapter adapter;
    List<Profile> profileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListProfilesBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String heading = getIntent().getStringExtra("heading");
        binding.headingTxt.setText(heading);

        profileList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            profileList.add(new Profile());
        }

        adapter = new VerticalProfilesAdapter(this, profileList);
        binding.listProfilesRc.setAdapter(adapter);
        binding.listProfilesRc.setLayoutManager(new LinearLayoutManager(this));

        binding.listProfilesRc.addItemDecoration(new RecyclerPaddingDecorator(
                UIUtil.dp(24), UIUtil.dp(24), UIUtil.dp(16), RecyclerView.VERTICAL
        ));

    }
}