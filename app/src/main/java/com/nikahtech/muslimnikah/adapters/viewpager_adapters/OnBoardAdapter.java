package com.nikahtech.muslimnikah.adapters.viewpager_adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.models.OnBoardItem;

import java.util.List;

public class OnBoardAdapter extends RecyclerView.Adapter<OnBoardAdapter.Holder> {

    private final List<OnBoardItem> list;

    public OnBoardAdapter(List<OnBoardItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_onboard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int pos) {
        OnBoardItem item = list.get(pos);
        h.image.setImageResource(item.imageRes);
        h.title.setText(item.title);
        h.description.setText(item.description);
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, description;

        public Holder(@NonNull View v) {
            super(v);
            image = v.findViewById(R.id.image);
            title = v.findViewById(R.id.title);
            description = v.findViewById(R.id.description);
        }
    }
}

