package com.nikahtech.muslimnikah.viewholders.connection_viewholders;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.databinding.ItemConnectionreqConnectedBinding;
import com.nikahtech.muslimnikah.models.ConnectionRequest;

public class ConnectedVH extends RecyclerView.ViewHolder {

    ItemConnectionreqConnectedBinding binding;

    public ConnectedVH(ItemConnectionreqConnectedBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ConnectionRequest item, Context context) {
    }
}
