package com.nikahtech.muslimnikah.viewholders.photoreq_viewholders;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.databinding.ItemPhotoreqReceivedBinding;
import com.nikahtech.muslimnikah.models.PhotoRequest;

public class ReceivedVH extends RecyclerView.ViewHolder {

    private ItemPhotoreqReceivedBinding binding;

    public ReceivedVH(ItemPhotoreqReceivedBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(PhotoRequest request, Context context) {

    }

}
