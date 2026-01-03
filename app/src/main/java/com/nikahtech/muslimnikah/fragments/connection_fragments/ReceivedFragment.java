package com.nikahtech.muslimnikah.fragments.connection_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.adapters.recyclerview_adapters.ConnectionRequestAdapter;
import com.nikahtech.muslimnikah.databinding.FragmentReceivedBinding;
import com.nikahtech.muslimnikah.enums.ConnectionRequestType;
import com.nikahtech.muslimnikah.models.ConnectionRequest;
import com.nikahtech.muslimnikah.utils.RecyclerPaddingDecorator;
import com.nikahtech.muslimnikah.utils.UIUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ReceivedFragment extends Fragment {

    FragmentReceivedBinding binding;
    List<ConnectionRequest> requestList = new ArrayList<>();

    public ReceivedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReceivedBinding.inflate(inflater, container, false);

        for (int i = 0; i < 10; i++) {
            requestList.add(new ConnectionRequest("1", "http://picsum/", "Peer", Instant.now(), ConnectionRequestType.RECEIVED));
        }

        ConnectionRequestAdapter adapter = new ConnectionRequestAdapter(requestList, requireContext());
        binding.receivedRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.receivedRc.setAdapter(adapter);
        binding.receivedRc.addItemDecoration(new RecyclerPaddingDecorator(
                UIUtil.dp(32), UIUtil.dp(32), UIUtil.dp(16), RecyclerView.VERTICAL
        ));

        return binding.getRoot();
    }
}