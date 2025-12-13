package com.nikahtech.muslimnikah.adapters.recyclerview_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.databinding.ItemChatActiveBinding;
import com.nikahtech.muslimnikah.databinding.ItemChatBinding;
import com.nikahtech.muslimnikah.models.Chat;
import com.nikahtech.muslimnikah.viewholders.chats.ChatsVH;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsVH> {

    Context context;
    List<Chat> chats;

    public ChatsAdapter(Context context, List<Chat> chats) {
        this.context = context;
        this.chats = chats;
    }

    @NonNull
    @Override
    public ChatsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemChatBinding binding =
                ItemChatBinding.inflate(inflater, parent, false);
        return new ChatsVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsVH holder, int position) {
        holder.bind(chats.get(position));
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}
