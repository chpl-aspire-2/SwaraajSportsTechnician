package com.swaraajsports.technician.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.swaraajsports.technician.response.NotificationResponce;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GuardNotificationAdapter extends RecyclerView.Adapter<GuardNotificationAdapter.MyViewHolder> {

    private final NotificationResponce notificationResponce;
    private final Context context;
    NotificatioInterFace notificatioInterFace;


    public interface NotificatioInterFace{
        void deleteClick(int pos, String data);
        void NotificationClick(NotificationResponce.Notification notification);
    }

    public void setUpInterface(NotificatioInterFace upInterface){
        this.notificatioInterFace=upInterface;
    }



    public GuardNotificationAdapter(NotificationResponce notificationResponce, Context context) {
        this.notificationResponce = notificationResponce;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custome_guardnotification, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder h, @SuppressLint("RecyclerView") final int i) {


        NotificationResponce.Notification notification=notificationResponce.getNotification().get(i);

        if (notificationResponce.getNotification().get(i).getNotificationLogo()!=null){
            BasicFunctions.displayImageBG(context,h.cIV,notificationResponce.getNotification().get(i).getNotificationLogo());
        }
        h.tv_noti_title.setText(notificationResponce.getNotification().get(i).getGuardNotificationTitle());
        h.tv_noti_desc.setText(notificationResponce.getNotification().get(i).getGuardNotificationDesc());
        h.tv_noti_date.setText(notificationResponce.getNotification().get(i).getGuardNotificationDate());

        h.iv_delete.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (notificatioInterFace != null) {
                    notificatioInterFace.deleteClick(i, notificationResponce.getNotification().get(i).getGuardNotificationId());
                }
            }
        });

        h.itemView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (notificatioInterFace != null) {
                    notificatioInterFace.NotificationClick(notification);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationResponce.getNotification().size();
    }

    @SuppressLint("NonConstantResourceId")
    static class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_noti_title)
        TextView tv_noti_title;
        @BindView(R.id.tv_noti_desc)
        TextView tv_noti_desc;
        @BindView(R.id.tv_noti_date)
        TextView tv_noti_date;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        @BindView(R.id.cIV)
        ImageView cIV;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
