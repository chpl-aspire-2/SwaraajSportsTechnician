package com.swaraajsports.technician.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.swaraajsports.technician.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChipDaynamicAdapter extends RecyclerView.Adapter<ChipDaynamicAdapter.ViewHolder> {

    List<String> selectedDays;

    public ChipDaynamicAdapter(List<String> selectedDays) {
        this.selectedDays = selectedDays;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chip_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvChip.setText(selectedDays.get(position).trim());
    }

    @Override
    public int getItemCount() {
        return selectedDays.size();
    }

    @SuppressLint("NonConstantResourceId")
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvChip)
        TextView tvChip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
