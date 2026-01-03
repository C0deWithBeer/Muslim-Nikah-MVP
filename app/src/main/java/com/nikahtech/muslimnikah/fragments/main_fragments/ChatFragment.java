package com.nikahtech.muslimnikah.fragments.main_fragments;

import static com.nikahtech.muslimnikah.utils.UIUtil.dp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.ActiveUsersAdapter;
import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.ChatsAdapter;
import com.nikahtech.muslimnikah.databinding.FragmentChatBinding;
import com.nikahtech.muslimnikah.models.Chat;
import com.nikahtech.muslimnikah.models.Profile;
import com.nikahtech.muslimnikah.utils.RecyclerPaddingDecorator;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    FragmentChatBinding binding;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);

        List<Profile> profiles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            profiles.add(new Profile("https://picsum.photos/200?1", "Aisha, 22"));
        }

        List<Chat> chats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            chats.add(new Chat("https://picsum.photos/200?1", "Peer Mohamed",
                    "Hey da how are you?", 38));
        }

        ActiveUsersAdapter activeUsersAdapter = new ActiveUsersAdapter(requireContext(), profiles);
        binding.activeUsersRc.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        binding.activeUsersRc.addItemDecoration(
                new RecyclerPaddingDecorator(
                        dp(16),  // first item = 16dp
                        dp(32),  // last item = 32dp
                        dp(8),   // spacing between items = 8dp
                        RecyclerView.HORIZONTAL
                )
        );

        binding.activeUsersRc.setAdapter(activeUsersAdapter);


        ChatsAdapter chatsAdapter = new ChatsAdapter(requireContext(), chats);
        binding.chatsRc.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        binding.chatsRc.addItemDecoration(
                new RecyclerPaddingDecorator(
                        dp(2),  // first item = 16dp
                        dp(2),  // last item = 32dp
                        dp(16),   // spacing between items = 24dp
                        RecyclerView.VERTICAL
                )
        );

        binding.chatsRc.setAdapter(chatsAdapter);

        return binding.getRoot();
    }
}