package com.nikahtech.muslimnikah.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nikahtech.muslimnikah.R;

import java.util.ArrayList;
import java.util.List;

public class SingleOptionPicker {

    private final Context context;
    private final List<?> items;  // full list
    private final View targetView;
    private final Object previousItem;
    private BottomSheetDialog bottomSheetDialog;
    private DynamicOptionAdapter adapter;
    private OptionSelectListener listener;
    private String category;

    public interface OptionSelectListener {
        void onOptionSelected(Object selectedItem);
    }

    public SingleOptionPicker(Context context, List<?> items, View targetView, Object previousItem) {
        this.context = context;
        this.items = items;
        this.targetView = targetView;
        this.previousItem = previousItem;
    }

    public SingleOptionPicker(Context context, List<?> items, View targetView, Object previousItem, OptionSelectListener listener) {
        this.context = context;
        this.items = items;
        this.targetView = targetView;
        this.previousItem = previousItem;
        this.listener = listener;
    }

    public SingleOptionPicker(Context context, List<?> items, View targetView, String category, Object previousItem, OptionSelectListener listener) {
        this.context = context;
        this.items = items;
        this.targetView = targetView;
        this.category = category;
        this.previousItem = previousItem;
        this.listener = listener;
    }

    // Open the bottom sheet
    public void open() {
        bottomSheetDialog = new BottomSheetDialog(context);
        View sheetView = LayoutInflater.from(context)
                .inflate(R.layout.single_option_sheet, null);
        bottomSheetDialog.setContentView(sheetView);

        // Disable swipe-to-dismiss
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        // Prevent bottom sheet from expanding to fullscreen on keyboard open
        bottomSheetDialog.getBehavior().setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        bottomSheetDialog.getBehavior().setSkipCollapsed(true);


        ImageView closeBtn = sheetView.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(view -> close());

        ImageView searchBtn = sheetView.findViewById(R.id.searchBtn);
        TextView categoryTxt = sheetView.findViewById(R.id.descTxt);
        categoryTxt.setText(category);

        TextInputLayout searchInputLay = sheetView.findViewById(R.id.searchInputLay);
        TextInputEditText searchEdt = sheetView.findViewById(R.id.searchEdt);
        searchBtn.setOnClickListener(view -> {
            if (searchInputLay.getVisibility() == View.VISIBLE){
                searchInputLay.setVisibility(View.GONE);
                return;
            }

            searchInputLay.setVisibility(View.VISIBLE);
            searchEdt.requestFocus();
        });


        RecyclerView recyclerView = sheetView.findViewById(R.id.rc);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new DynamicOptionAdapter(items, previousItem, selectedItem -> {
            if (targetView != null) {
                targetView.clearFocus();
            }
            if (targetView instanceof TextView) {
                ((TextView) targetView).setText(selectedItem != null ? selectedItem.toString() : "");
            }
            if (listener != null) {
                listener.onOptionSelected(selectedItem != null ? selectedItem.toString() : "");
            }
            bottomSheetDialog.dismiss();
        });

        recyclerView.setAdapter(adapter);

        // Search functionality
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        bottomSheetDialog.show();
    }

    // Close the bottom sheet
    public void close() {
        if (targetView != null) {
            targetView.clearFocus();
        }

        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    // Adapter for options
    private static class DynamicOptionAdapter extends RecyclerView.Adapter<DynamicOptionAdapter.ViewHolder> {

        interface OnItemClickListener {
            void onItemClick(Object selectedItem);
        }

        private final List<?> fullList;   // keep original full list
        private final List<Object> filteredList; // dynamic list
        private final Object previousItem;
        private final OnItemClickListener listener;
        private int selectedPosition = -1;

        public DynamicOptionAdapter(List<?> items, Object previousItem, OnItemClickListener listener) {
            this.fullList = new ArrayList<>(items != null ? items : new ArrayList<>());
            this.filteredList = new ArrayList<>(items != null ? items : new ArrayList<>());
            this.previousItem = previousItem;
            this.listener = listener;

            // Set previously selected item position
            if (previousItem != null) {
                for (int i = 0; i < filteredList.size(); i++) {
                    if (previousItem.equals(filteredList.get(i))) {
                        selectedPosition = i;
                        break;
                    }
                }
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_selectable_option, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Object item = filteredList.get(position);
            holder.bind(item, position == selectedPosition);

            View.OnClickListener clickListener = v -> {
                int currentPos = holder.getAdapterPosition();
                if (currentPos == RecyclerView.NO_POSITION) return;
                selectedPosition = currentPos;
                notifyDataSetChanged();
                listener.onItemClick(filteredList.get(currentPos));
            };

            holder.itemView.setOnClickListener(clickListener);
            holder.checkBox.setOnClickListener(clickListener);
        }

        @Override
        public int getItemCount() {
            return filteredList.size();
        }

        // Filtering logic
        public void filter(String query) {
            filteredList.clear();
            if (query == null || query.trim().isEmpty()) {
                filteredList.addAll(fullList);
            } else {
                String lowerCaseQuery = query.toLowerCase();
                for (Object item : fullList) {
                    if (item != null && item.toString().toLowerCase().contains(lowerCaseQuery)) {
                        filteredList.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView optionTextView;
            private final CheckBox checkBox;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                optionTextView = itemView.findViewById(R.id.optionTxt);
                checkBox = itemView.findViewById(R.id.chkbox);
            }

            public void bind(Object item, boolean isChecked) {
                optionTextView.setText(item != null ? item.toString() : "");
                checkBox.setChecked(isChecked);
            }
        }
    }
}
