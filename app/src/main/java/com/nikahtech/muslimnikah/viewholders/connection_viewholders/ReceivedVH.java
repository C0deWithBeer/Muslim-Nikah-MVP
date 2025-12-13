package com.nikahtech.muslimnikah.viewholders.connection_viewholders;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.databinding.ItemConnectionreqReceivedBinding;
import com.nikahtech.muslimnikah.models.ConnectionRequest;

public class ReceivedVH extends RecyclerView.ViewHolder {

    ItemConnectionreqReceivedBinding binding;

    public ReceivedVH(ItemConnectionreqReceivedBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ConnectionRequest item, Context context) {

    }
}
