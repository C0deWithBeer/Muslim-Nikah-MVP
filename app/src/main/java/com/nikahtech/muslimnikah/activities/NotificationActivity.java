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
import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.NotificationAdapter;
import com.nikahtech.muslimnikah.databinding.ActivityNotificationBinding;
import com.nikahtech.muslimnikah.models.Notification;
import com.nikahtech.muslimnikah.utils.RecyclerPaddingDecorator;
import com.nikahtech.muslimnikah.utils.UIUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;
    NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<Notification> notificationList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            notificationList.add(new Notification("Mohammed Ibrahim", "sent a connection request to you", 1, Instant.now()));
        }

        adapter = new NotificationAdapter(this, notificationList);
        binding.notificationsRc.setLayoutManager(new LinearLayoutManager(this));
        binding.notificationsRc.setAdapter(adapter);

        binding.notificationsRc.addItemDecoration(new RecyclerPaddingDecorator(
                UIUtil.dp(24), UIUtil.dp(24), UIUtil.dp(16), RecyclerView.VERTICAL
        ));


    }
}