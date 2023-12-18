package com.swaraajsports.technician.attendance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.swaraajsports.technician.R;
import com.swaraajsports.technician.response.EmployeeAttendanceResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LastAttendanceViewAdapter extends RecyclerView.Adapter<LastAttendanceViewAdapter.MyBillHolder>{

    Context context;
    List<EmployeeAttendanceResponse.Summary> lastBill;

    public LastAttendanceViewAdapter(Context context, List<EmployeeAttendanceResponse.Summary> lastBill) {
        this.context = context;
        this.lastBill = lastBill;
    }



    @NonNull
    @Override
    public MyBillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_last_emp_attend, parent, false);

        return new MyBillHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyBillHolder h, int position) {

        h.tv_month.setText(lastBill.get(position).getMonthName()+"");
        h.tv_working_day.setText(lastBill.get(position).getTotalWorkingDays()+"");
        h.tv_absunt_days.setText(lastBill.get(position).getAbsuntDays()+"");
        h.tv_total_days.setText(lastBill.get(position).getTotalDaysMonth()+"");

        /*if (lastBill.get(position).getPayStatus().equalsIgnoreCase("paid")){
            h.status.setTextColor(ContextCompat.getColor(context, R.color.bill_paid_text));
            h.status.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_green_light));


        }else {

            h.status.setTextColor(ContextCompat.getColor(context, R.color.bill_unpaid_text));
            h.status.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_red_light));

        }*/

    }

    @Override
    public int getItemCount() {
        return lastBill.size();
    }

    @SuppressLint("NonConstantResourceId")
    static class MyBillHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.tv_month)
        TextView tv_month;
        @BindView(R.id.tv_working_day)
        TextView tv_working_day;
        @BindView(R.id.tv_absunt_days)
        TextView tv_absunt_days;
        @BindView(R.id.tv_total_days)
        TextView tv_total_days;


        public MyBillHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
