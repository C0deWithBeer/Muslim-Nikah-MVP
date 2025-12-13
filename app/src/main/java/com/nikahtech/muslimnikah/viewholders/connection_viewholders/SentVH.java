package com.nikahtech.muslimnikah.viewholders.connection_viewholders;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.databinding.ItemConnectionreqSentBinding;
import com.nikahtech.muslimnikah.models.ConnectionRequest;

public class SentVH extends RecyclerView.ViewHolder {

    ItemConnectionreqSentBinding binding;

    public SentVH(ItemConnectionreqSentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ConnectionRequest item, Context context) {

    }
}
