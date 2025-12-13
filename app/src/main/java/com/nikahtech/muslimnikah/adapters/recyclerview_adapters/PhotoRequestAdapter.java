package com.nikahtech.muslimnikah.adapters.recyclerview_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikahtech.muslimnikah.constants.DashboardSectionType;
import com.nikahtech.muslimnikah.constants.PhotoRequestType;
import com.nikahtech.muslimnikah.databinding.ItemDashHeaderBinding;
import com.nikahtech.muslimnikah.databinding.ItemPhotoreqReceivedBinding;
import com.nikahtech.muslimnikah.databinding.ItemPhotoreqSentBinding;
import com.nikahtech.muslimnikah.interfaces.DashboardSection;
import com.nikahtech.muslimnikah.models.PhotoRequest;
import com.nikahtech.muslimnikah.models.dashboard_sections.DailyMatches;
import com.nikahtech.muslimnikah.models.dashboard_sections.Header;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.DailyMatchesVH;
import com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders.HeaderVH;
import com.nikahtech.muslimnikah.viewholders.photoreq_viewholders.ReceivedVH;
import com.nikahtech.muslimnikah.viewholders.photoreq_viewholders.SentVH;

import java.util.List;

public class PhotoRequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<PhotoRequest> requestList;

    public PhotoRequestAdapter(Context context, List<PhotoRequest> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @Override
    public int getItemViewType(int position) {
        return requestList.get(position).getType().getType();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {

            case PhotoRequestType.SENT:
                ItemPhotoreqSentBinding sentBinding =
                        ItemPhotoreqSentBinding.inflate(inflater, parent, false);
                return new SentVH(sentBinding);

            case PhotoRequestType.RECEIVED:
                ItemPhotoreqReceivedBinding receivedBinding =
                        ItemPhotoreqReceivedBinding.inflate(inflater, parent, false);
                return new ReceivedVH(receivedBinding);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        PhotoRequest request = requestList.get(position);

        switch (request.getType().getType()) {
            case PhotoRequestType.SENT:
                SentVH sentVH = (SentVH) holder;
                sentVH.bind(request, context);
                break;

            case PhotoRequestType.RECEIVED:
                ReceivedVH receivedVH = (ReceivedVH) holder;
                receivedVH.bind(request, context);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
