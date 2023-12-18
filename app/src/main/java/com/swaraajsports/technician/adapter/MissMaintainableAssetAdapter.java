package com.swaraajsports.technician.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.response.MissingAssetResponse;

import java.util.List;

public class MissMaintainableAssetAdapter extends RecyclerView.Adapter<MissMaintainableAssetAdapter.MyHolder>{


    private List<MissingAssetResponse.Maintenance> data;
    private Context context;
    private ClickRow clickRow;

    public void setUp(ClickRow up){
        this.clickRow=up;
    }

    public interface ClickRow{
        void click(MissingAssetResponse.Maintenance as);
    }


    public MissMaintainableAssetAdapter(List<MissingAssetResponse.Maintenance> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assets_maintenance_pending_completed, parent, false);

        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvPName.setText(data.get(position).getAssetsName());
        holder.tvAbout.setText(data.get(position).getAssetsBrandName());

        holder.tvLName.setText(data.get(position).getAssetsLocation());

        holder.tvDateSc.setText(data.get(position).getMaintenanceScheduleDate());

        holder.tvCName.setText(data.get(position).getAssetsCategory());


        if (data.get(position).getMaintenanceCompleteAt()!=null && data.get(position).getMaintenanceCompleteAt().length()>2){
            holder.tvTitleDateCom.setVisibility(View.VISIBLE);
            holder.tvDateCom.setVisibility(View.VISIBLE);
            holder.tvDateCom.setText(data.get(position).getMaintenanceCompleteAt());
        }else {
            holder.tvTitleDateCom.setVisibility(View.GONE);
            holder.tvDateCom.setVisibility(View.GONE);
        }

        if (data.get(position).getRemark()!=null){
            holder.tvTitleRemark.setVisibility(View.VISIBLE);
            holder.tvRemark.setVisibility(View.VISIBLE);
            holder.tvRemark.setText(data.get(position).getRemark());

            if (data.get(position).getMaintenancePhoto()!=null && data.get(position).getMaintenancePhoto().length()>5){

                holder.ivImageRemark.setVisibility(View.VISIBLE);
                BasicFunctions.displayImage(context,holder.ivImageRemark,
                        data.get(position).getMaintenancePhoto(),R.drawable.image_view,R.drawable.image_view);


            }else {
                holder.ivImageRemark.setVisibility(View.GONE);
            }

        }else {

            if (data.get(position).getMaintenancePhoto()!=null && data.get(position).getMaintenancePhoto().length()>5){

                holder.ivImageRemark.setVisibility(View.VISIBLE);
                BasicFunctions.displayImage(context,holder.ivImageRemark,
                        data.get(position).getMaintenancePhoto(),R.drawable.image_view,R.drawable.image_view);


            }else {
                holder.tvTitleRemark.setVisibility(View.GONE);
                holder.ivImageRemark.setVisibility(View.GONE);
                holder.tvRemark.setVisibility(View.GONE);
            }

        }




        if (data.get(position).getMaintenanceAmount()!=null){
            holder.tvMainAmTitle.setVisibility(View.VISIBLE);
            holder.tvAmount.setVisibility(View.VISIBLE);
            holder.tvAmount.setText(data.get(position).getMaintenanceAmount());

            if (data.get(position).getMaintenance_invoice()!=null && data.get(position).getMaintenance_invoice().length()>5){

                holder.ivImageInvoce.setVisibility(View.VISIBLE);
                BasicFunctions.displayImage(context,holder.ivImageInvoce,
                        data.get(position).getMaintenance_invoice(),R.drawable.image_view,R.drawable.image_view);


            }else {
                holder.ivImageInvoce.setVisibility(View.GONE);
            }

        }else {

            if (data.get(position).getMaintenance_invoice()!=null && data.get(position).getMaintenance_invoice().length()>5){

                holder.ivImageInvoce.setVisibility(View.VISIBLE);
                BasicFunctions.displayImage(context,holder.ivImageInvoce,
                        data.get(position).getMaintenance_invoice(),R.drawable.image_view,R.drawable.image_view);

            }else {
                holder.tvMainAmTitle.setVisibility(View.GONE);
                holder.ivImageInvoce.setVisibility(View.GONE);
                holder.tvAmount.setVisibility(View.GONE);
            }

        }


        BasicFunctions.displayImage(context,holder.cirImage,
                data.get(position).getAssetsFile(),R.drawable.logo,R.drawable.logo);



        holder.ivImageInvoce.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(data.get(position).getMaintenance_invoice()));
                context.startActivity(i);

            }
        });

        holder.ivImageRemark.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(data.get(position).getMaintenancePhoto()));
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView cirImage,ivImageRemark,ivImageInvoce;
        TextView tvPName,tvAbout,tvCName,tvLName,tvDateSc,tvDateCom,tvRemark,tvAmount,tvTitleDateCom,tvTitleRemark,tvMainAmTitle;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            cirImage = itemView.findViewById(R.id.cirImage);
            tvPName = itemView.findViewById(R.id.tvPName);
            tvAbout = itemView.findViewById(R.id.tvAbout);
            tvCName = itemView.findViewById(R.id.tvCName);
            tvLName = itemView.findViewById(R.id.tvLName);
            tvMainAmTitle = itemView.findViewById(R.id.tvMainAmTitle);
            tvDateSc = itemView.findViewById(R.id.tvDateSc);
            tvDateCom = itemView.findViewById(R.id.tvDateCom);
            tvRemark = itemView.findViewById(R.id.tvRemark);
            ivImageRemark = itemView.findViewById(R.id.ivImageRemark);
            ivImageInvoce = itemView.findViewById(R.id.ivImageInvoce);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvTitleDateCom = itemView.findViewById(R.id.tvTitleDateCom);
            tvTitleRemark = itemView.findViewById(R.id.tvTitleRemark);
        }
    }
}
