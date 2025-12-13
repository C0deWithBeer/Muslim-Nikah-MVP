package com.nikahtech.muslimnikah.adapters.recyclerview_adapters.skeleton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.R;

public class SkeletonVerticalAdapter extends RecyclerView.Adapter<SkeletonVerticalAdapter.VH> {

    private final int count;

    public SkeletonVerticalAdapter(int count) {
        this.count = count;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skeleton_vertical_profile, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) { }

    @Override
    public int getItemCount() {
        return count;
    }

    static class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) { super(itemView); }
    }
}

