package com.swaraajsports.technician.selectsociety.adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.response.SocietyResponse;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SocietyAdapter extends RecyclerView.Adapter<SocietyAdapter.MySocietyHolder> {

    private final Context ctx;
    private List<SocietyResponse.Society> societyResponce;
    private SocietyInterface societyInterface;

    public SocietyAdapter(Context ctx, List<SocietyResponse.Society> societyResponce) {
        this.ctx = ctx;
        this.societyResponce = societyResponce;
    }

    public void update() {
        notifyDataSetChanged();
    }

    private void set() {
        for (SocietyResponse.Society st : societyResponce) {
            st.setClicked(false);
        }
    }

    public void setUpInterface(SocietyInterface upInterface) {
        this.societyInterface = upInterface;
    }

    @NonNull
    @Override
    public MySocietyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_society, parent, false);
        return new MySocietyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MySocietyHolder h, @SuppressLint("RecyclerView") final int i) {

        h.tv_society_title.setText(societyResponce.get(i).getSocietyName());
        h.tv_society_address.setText(societyResponce.get(i).getSocietyAddress());
        if (societyResponce.get(i).isClicked()) {

            h.lin_root.setBackgroundColor(ctx.getResources().getColor(R.color.colorPrimaryDark));
            h.tv_society_title.setTextColor(ctx.getResources().getColor(R.color.white));
            h.tv_society_address.setTextColor(ctx.getResources().getColor(R.color.white));
            h.itemView.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    set();
                    societyInterface.click(societyResponce.get(i).getSocietyId(), societyResponce.get(i).getSocietyName(), i, societyResponce.get(i).getSubDomain(), societyResponce.get(i).getApiKey(), societyResponce.get(i),
                            societyResponce.get(i).isClicked());
                }
            });

        } else {


            h.lin_root.setBackgroundColor(ctx.getResources().getColor(R.color.white));
            h.tv_society_title.setTextColor(ctx.getResources().getColor(R.color.black));
            h.tv_society_address.setTextColor(ctx.getResources().getColor(R.color.black));
            h.itemView.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    set();
                    societyResponce.get(i).setClicked(true);
                    societyInterface.click(societyResponce.get(i).getSocietyId(), societyResponce.get(i).getSocietyName(), i, societyResponce.get(i).getSubDomain(), societyResponce.get(i).getApiKey(), societyResponce.get(i),
                            societyResponce.get(i).isClicked());
                }
            });

        }


    }

    public void Update(List<SocietyResponse.Society> societyResponce1) {
        societyResponce = societyResponce1;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return societyResponce.size();
    }

    public interface SocietyInterface {
        void click(String data, String sname, int pos, String baseurl, String apikey, SocietyResponse.Society society, boolean isClick);
    }

    class MySocietyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_society_logo)
        CircleImageView iv_society_logo;
        @BindView(R.id.iv_society_logo1)
        ImageView iv_society_logo1;

        @BindView(R.id.tv_society_title)
        TextView tv_society_title;

        @BindView(R.id.tv_society_address)
        TextView tv_society_address;

        @BindView(R.id.lin_root)
        RelativeLayout lin_root;

        MySocietyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
