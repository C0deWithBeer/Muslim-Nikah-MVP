package com.nikahtech.muslimnikah.adapters.recyclerview_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.databinding.ItemConnectionreqConnectedBinding;
import com.nikahtech.muslimnikah.databinding.ItemConnectionreqReceivedBinding;
import com.nikahtech.muslimnikah.databinding.ItemConnectionreqSentBinding;
import com.nikahtech.muslimnikah.enums.ConnectionRequestType;
import com.nikahtech.muslimnikah.models.ConnectionRequest;
import com.nikahtech.muslimnikah.viewholders.connection_viewholders.ConnectedVH;
import com.nikahtech.muslimnikah.viewholders.connection_viewholders.ReceivedVH;
import com.nikahtech.muslimnikah.viewholders.connection_viewholders.SentVH;

import java.util.List;

public class ConnectionRequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ConnectionRequest> items;
    private final Context context;

    public ConnectionRequestAdapter(List<ConnectionRequest> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position)
                .getConnectionRequestType()
                .getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == ConnectionRequestType.SENT.getType()) {
            ItemConnectionreqSentBinding b =
                    ItemConnectionreqSentBinding.inflate(inflater, parent, false);
            return new SentVH(b);
        }

        else if (viewType == ConnectionRequestType.RECEIVED.getType()) {
            ItemConnectionreqReceivedBinding b =
                    ItemConnectionreqReceivedBinding.inflate(inflater, parent, false);
            return new ReceivedVH(b);
        }

        else if (viewType == ConnectionRequestType.CONNECTED.getType()) {
            ItemConnectionreqConnectedBinding b =
                    ItemConnectionreqConnectedBinding.inflate(inflater, parent, false);
            return new ConnectedVH(b);
        }

        throw new IllegalArgumentException("Unknown view type: " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ConnectionRequest item = items.get(position);

        int type = item.getConnectionRequestType().getType();

        if (type == ConnectionRequestType.SENT.getType()) {
            ((SentVH) holder).bind(item, context);
        }

        else if (type == ConnectionRequestType.RECEIVED.getType()) {
            ((ReceivedVH) holder).bind(item, context);
        }

        else if (type == ConnectionRequestType.CONNECTED.getType()) {
            ((ConnectedVH) holder).bind(item, context);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

