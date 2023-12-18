package com.swaraajsports.technician.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.response.AssetDetailResponse;

import java.util.List;

public class AssetScheduleAdapter extends RecyclerView.Adapter<AssetScheduleAdapter.MyHolder>{


    private List<AssetDetailResponse.Schedule> data;
    private Context context;
    private ClickRow clickRow;

    public void setUp(ClickRow up){
        this.clickRow=up;
    }

    public interface ClickRow{
        void click(AssetDetailResponse.Schedule as);
    }


    public AssetScheduleAdapter(List<AssetDetailResponse.Schedule> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assets_schdule_maintenance, parent, false);

        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvPName.setText(data.get(position).getType());
        holder.tvAbout.setText(data.get(position).getMaintenanceDay() +" "+data.get(position).getMaintenanceDate());
        holder.tvLName.setText(data.get(position).getVendorName()+" "+data.get(position).getVendorMobile());


        try {
            String[] separated = data.get(position).getMaintenanceDate().split(" ");

            holder.tv_date.setText(separated[0]);
            holder.tv_month.setText(separated[1]);
            holder.tv_year.setText(separated[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }


       if (data.get(position).getIsCompleted()){
           holder.tvCName.setText("Work Completed");
           holder.tvCName.setTextColor(ContextCompat.getColor(context,R.color.green_800));
           holder.tvCName.setBackground(ContextCompat.getDrawable(context,R.drawable.status_success));

       }else {
           holder.tvCName.setTextColor(ContextCompat.getColor(context,R.color.yellow_800));
           holder.tvCName.setBackground(ContextCompat.getDrawable(context,R.drawable.status_pending));
           holder.tvCName.setText("Work Pending");

       }

       if (data.get(position).getCompletedAction()){

           if (!data.get(position).getIsCompleted()) {
               holder.tvComplete.setVisibility(View.VISIBLE);
           }else {
               holder.tvComplete.setVisibility(View.GONE);
           }
       }else {
           holder.tvComplete.setVisibility(View.GONE);
       }

        holder.tvComplete.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (clickRow!=null){
                    clickRow.click(data.get(position));
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView tvPName,tvAbout,tvCName,tvLName,tv_year,tv_date,tv_month;
        TextView tvComplete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvPName = itemView.findViewById(R.id.tvPName);
            tvAbout = itemView.findViewById(R.id.tvAbout);
            tvCName = itemView.findViewById(R.id.tvCName);
            tvLName = itemView.findViewById(R.id.tvLName);
            tvComplete = itemView.findViewById(R.id.tvComplete);

            tv_year = itemView.findViewById(R.id.tv_year);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_month = itemView.findViewById(R.id.tv_month);

        }
    }
}
