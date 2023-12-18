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
import com.swaraajsports.technician.util.PreferenceManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyAttendHolder> {

    List<EmployeeAttendanceResponse.InOutTime> inOutTime;
    Context context;

    PreferenceManager preferenceManager;

    public AttendanceAdapter(List<EmployeeAttendanceResponse.InOutTime> inOutTime, Context context) {
        this.inOutTime = inOutTime;
        this.context = context;
        preferenceManager = new PreferenceManager(context);
    }


    @NonNull
    @Override
    public MyAttendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_my_resources_atten_time, parent, false);
        return new MyAttendHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyAttendHolder holder, int position) {

        holder.tv_time_in.setText("In Time "+inOutTime.get(position).getIntTime());

        holder.tv_time_out.setText("Out Time "+inOutTime.get(position).getOutTime());

        holder.tv_index.setText(""+(position+1)+".");

    }

    @Override
    public int getItemCount() {
        return inOutTime.size();
    }

    @SuppressLint("NonConstantResourceId")
    static class MyAttendHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_index)
        TextView tv_index;
        @BindView(R.id.tv_time_in)
        TextView tv_time_in;
        @BindView(R.id.tv_time_out)
        TextView tv_time_out;

        public MyAttendHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
