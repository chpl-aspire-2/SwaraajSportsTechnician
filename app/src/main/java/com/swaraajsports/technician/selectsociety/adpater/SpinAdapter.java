package com.swaraajsports.technician.selectsociety.adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.selectsociety.LocationHelper;

import java.util.List;

public class SpinAdapter extends RecyclerView.Adapter<SpinAdapter.AreaListViewHolder> {
    private static AreaSingleClickListener sClickListener;
    private final Context context;
    private List<LocationHelper> locationHelpers;
    private int sSelected = -1;
    private String RecordId;

    public SpinAdapter(Context context, List<LocationHelper> locationHelpers, int sSelected, String RecordId) {
        this.context = context;
        this.locationHelpers = locationHelpers;
        this.sSelected = sSelected;
        this.RecordId = RecordId;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateSearch(List<LocationHelper> filterlocationHelpers) {
        locationHelpers = filterlocationHelpers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AreaListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.area_list_raw, parent, false);
        return new AreaListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.selectAreaRb.setChecked(false);
        holder.selectAreaRb.setText(locationHelpers.get(position).name.trim());
        holder.selectAreaRb.setChecked(RecordId.equalsIgnoreCase(locationHelpers.get(position).id));
        holder.itemView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                sSelected = position;
                RecordId = locationHelpers.get(position).id;

                if (sClickListener != null) {
                    sClickListener.onItemClickListener(locationHelpers.get(position).name, locationHelpers.get(position).id, position,locationHelpers.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationHelpers.size();
    }

    public void setOnItemClickListener(AreaSingleClickListener clickListener) {
        sClickListener = clickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void selectedItem() {
        notifyDataSetChanged();
    }

    public interface AreaSingleClickListener {
        void onItemClickListener(String name, String id, int position,LocationHelper help);
    }

    public static class AreaListViewHolder extends RecyclerView.ViewHolder {
        RadioButton selectAreaRb;

        AreaListViewHolder(View itemView) {
            super(itemView);
            selectAreaRb = itemView.findViewById(R.id.select_area_rb);
        }


    }

}