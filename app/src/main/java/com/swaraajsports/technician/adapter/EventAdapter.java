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
import com.swaraajsports.technician.response.EventListResponce;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyHolder> {

    private List<EventListResponce.EventList> event;
    private final Context context;
    private  ClickEvent clickEvent;

    public EventAdapter(List<EventListResponce.EventList> event, Context context) {
        this.event = event;
        this.context = context;
    }

    public interface ClickEvent{
        void Scan(EventListResponce.EventList eventList);
        void Detail(EventListResponce.EventList eventList);
    }

    public void update(List<EventListResponce.EventList> el){
        this.event = el;
        notifyDataSetChanged();
    }


    public void setUp(ClickEvent up){
        this.clickEvent=up;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_event, parent, false);
        return new MyHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_index.setText("#"+(position+1));

        BasicFunctions.displayImageBG(context,holder.iv_event_image,event.get(position).getEventImage());
        holder.tv_event_name.setText(event.get(position).getEventTitle());

        holder.tv_scan.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (clickEvent!=null){
                    clickEvent.Scan(event.get(position));
                }

            }
        });


        holder.tv_detail.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (clickEvent!=null){
                    clickEvent.Detail(event.get(position));
                }

            }
        });


        holder.tv_start_date.setText("Start Date : "+event.get(position).getEventStartDateView());
        holder.tv_end_date.setText("End Date : "+event.get(position).getEventEndDate());

        if (event.get(position).getTotalPass()!=null){
            holder.tv_total_pass.setText(event.get(position).getTotalPass());
        }else {
            holder.tv_total_pass.setText("00");
        }

        if (event.get(position).getVerifiedPass()!=null){
            holder.tv_total_verifed.setText(event.get(position).getVerifiedPass());
        }else {
            holder.tv_total_verifed.setText("00");
        }

        if (event.get(position).getPendingPass()!=null){
            holder.tv_total_pending.setText(event.get(position).getPendingPass());
        }else {
            holder.tv_total_pending.setText("00");
        }

    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    @SuppressLint("NonConstantResourceId")
    static class MyHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_event_image)
        ImageView iv_event_image;

        @BindView(R.id.tv_event_name)
        TextView tv_event_name;

        @BindView(R.id.tv_start_date)
        TextView tv_start_date;

        @BindView(R.id.tv_end_date)
        TextView tv_end_date;

        @BindView(R.id.tv_total_pass)
        TextView tv_total_pass;

        @BindView(R.id.tv_total_verifed)
        TextView tv_total_verifed;

        @BindView(R.id.tv_total_pending)
        TextView tv_total_pending;

        @BindView(R.id.tv_scan)
        TextView tv_scan;
        @BindView(R.id.tv_detail)
        TextView tv_detail;
        @BindView(R.id.tv_index)
        TextView tv_index;



        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
