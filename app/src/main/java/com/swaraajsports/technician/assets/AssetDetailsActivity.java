package com.swaraajsports.technician.assets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.activity.BaseActivityClass;
import com.swaraajsports.technician.response.AssetListResponse;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class AssetDetailsActivity extends BaseActivityClass {

    @BindView(R.id.lost_found_activity_iv_img)
    ImageView lostFoundActivityIvImg;
    @BindView(R.id.llDetails)
    LinearLayout llDetails;
    @BindView(R.id.addlostandfound_tvItem)
    TextView addlostandfoundTvItem;
    @BindView(R.id.lost_found_activity_tv_title)
    TextView lostFoundActivityTvTitle;
    @BindView(R.id.addlostandfound_tvdescription)
    TextView addlostandfoundTvdescription;
    @BindView(R.id.lost_found_activity_tv_desc)
    TextView lostFoundActivityTvDesc;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.lin_profile)
    LinearLayout linProfile;
    @BindView(R.id.addlostandfound_tvName)
    TextView addlostandfoundTvName;
    @BindView(R.id.lost_found_activity_tv_name)
    TextView lostFoundActivityTvName;
    @BindView(R.id.addlostandfound_tvmobile)
    TextView addlostandfoundTvmobile;
    @BindView(R.id.lost_found_activity_tv_mobile)
    TextView lostFoundActivityTvMobile;
    @BindView(R.id.lost_found_activity_btn_delete)
    com.ajitmaurya.basicsetup.view.CustomButton lostFoundActivityBtnDelete;
    @BindView(R.id.lost_found_activity_btn_chat)
    com.ajitmaurya.basicsetup.view.CustomButton lostFoundActivityBtnChat;

    @BindView(R.id.ivBack)
            ImageView ivBack;

    @BindView(R.id.tv_title)
    TextView tv_title;

    AssetListResponse.Asset as;

    @Override
    protected int getContentView() {
        return R.layout.activity_asset_details;//your layout
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){

            as = (AssetListResponse.Asset) bundle.getSerializable("as");
            tv_title.setText(as.getAssetsName());


            BasicFunctions.displayImage(this,lostFoundActivityIvImg,as.getAssetsFile(),R.drawable.logo,R.drawable.logo);

            lostFoundActivityTvTitle.setText(as.getAssetsName());
            lostFoundActivityTvDesc.setText(as.getAssetsCategory()+"\n"+
                    as.getAssetsBrandName()+"\n"+
                    as.getItemPurchaseDate());

            lostFoundActivityTvName.setText(as.getHandoverDate());

            lostFoundActivityTvMobile.setText(as.getAssetsLocation());
        }

        ivBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

    }
}