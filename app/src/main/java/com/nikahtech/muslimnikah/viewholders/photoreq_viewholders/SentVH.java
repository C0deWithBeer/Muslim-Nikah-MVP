package com.nikahtech.muslimnikah.viewholders.photoreq_viewholders;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.databinding.ItemPhotoreqSentBinding;
import com.nikahtech.muslimnikah.models.PhotoRequest;

public class SentVH extends RecyclerView.ViewHolder {

    private ItemPhotoreqSentBinding binding;

    public SentVH(ItemPhotoreqSentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(PhotoRequest request, Context context) {

    }

}
