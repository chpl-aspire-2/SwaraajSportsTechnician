package com.swaraajsports.technician.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.response.AssetDetailResponse;

import java.util.List;

public class MaintainableAssetAdapter extends RecyclerView.Adapter<MaintainableAssetAdapter.MyHolder>{


    private List<AssetDetailResponse.Maintenance> data;
    private Context context;
    private ClickRow clickRow;

    public void setUp(ClickRow up){
        this.clickRow=up;
    }

    public interface ClickRow{
        void click(AssetDetailResponse.Maintenance as,AssetDetailResponse.Schedule scdule);
    }


    public MaintainableAssetAdapter(List<AssetDetailResponse.Maintenance> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assets_maintenance, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvPName.setText(data.get(position).getAssetsName());
        holder.tvAbout.setText(data.get(position).getAssetsBrandName());
        holder.tvLName.setText(data.get(position).getAssetsLocation());
        holder.tvCName.setText(data.get(position).getAssetsCategory());


        BasicFunctions.displayImage(context,holder.cirImage,
                data.get(position).getAssetsFile(),R.drawable.logo,R.drawable.logo);



        if (data.get(position).getSchedule()!=null && data.get(position).getSchedule().size()>0){

            holder.recyScdule.setVisibility(View.VISIBLE);
            holder.recyScdule.setLayoutManager(new LinearLayoutManager(context));

            AssetScheduleAdapter assetScheduleAdapter = new AssetScheduleAdapter(data.get(position).getSchedule(),context);
            holder.recyScdule.setAdapter(assetScheduleAdapter);

            assetScheduleAdapter.setUp(new AssetScheduleAdapter.ClickRow() {
                @Override
                public void click(AssetDetailResponse.Schedule as) {

                    if (clickRow!=null){
                        clickRow.click(data.get(position),as);
                    }


                }
            });

        }else {

            holder.recyScdule.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView cirImage;
        TextView tvPName,tvAbout,tvCName,tvLName;
        RecyclerView recyScdule;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cirImage = itemView.findViewById(R.id.cirImage);
            tvPName = itemView.findViewById(R.id.tvPName);
            tvAbout = itemView.findViewById(R.id.tvAbout);
            tvCName = itemView.findViewById(R.id.tvCName);
            tvLName = itemView.findViewById(R.id.tvLName);
            recyScdule = itemView.findViewById(R.id.recyScdule);
        }
    }
}
