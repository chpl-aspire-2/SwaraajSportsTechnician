package com.swaraajsports.technician.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.response.AssetListResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.MyHolder>{


    private List<AssetListResponse.Asset> data;
    private Context context;
    private ClickRow clickRow;

    public void setUp(ClickRow up){
        this.clickRow=up;
    }

    public interface ClickRow{
        void click(AssetListResponse.Asset as);
    }


    public AssetAdapter(List<AssetListResponse.Asset> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.today_assets_maintenance, parent, false);

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


        holder.itemView.setOnClickListener(new OnSingleClickListener() {
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

        CircleImageView cirImage;
        TextView tvPName,tvAbout,tvCName,tvLName;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cirImage = itemView.findViewById(R.id.cirImage);
            tvPName = itemView.findViewById(R.id.tvPName);
            tvAbout = itemView.findViewById(R.id.tvAbout);
            tvCName = itemView.findViewById(R.id.tvCName);
            tvLName = itemView.findViewById(R.id.tvLName);
        }
    }
}
