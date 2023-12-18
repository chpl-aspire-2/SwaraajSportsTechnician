package com.swaraajsports.technician.assets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.activity.BaseActivityClass;
import com.swaraajsports.technician.adapter.AssetAdapter;
import com.swaraajsports.technician.response.AssetListResponse;

import butterknife.BindView;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class AssetListActivity extends BaseActivityClass {

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.recyData)
    RecyclerView recyData;

    @BindView(R.id.psBar)
    ProgressBar psBar;

    @BindView(R.id.tvNoData)
    TextView tvNoData;


    @Override
    protected int getContentView() {
        return R.layout.activity_asset_list;//your layout
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        recyData.setLayoutManager(new LinearLayoutManager(this));



        psBar.setVisibility(View.VISIBLE);
        recyData.setVisibility(View.GONE);


        getApiCallData();

        ivBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }

    private void getApiCallData() {

        getCallSociety().getAssets("getAssets",
                preferenceManager.getSocietyId(),
                preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<AssetListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> {

                            psBar.setVisibility(View.GONE);
                            recyData.setVisibility(View.GONE);

                        });
                    }

                    @Override
                    public void onNext(AssetListResponse assetListResponse) {

                        runOnUiThread(() -> {

                            if (assetListResponse.getAssets()!=null && assetListResponse.getAssets().size()>0){
                                tvNoData.setVisibility(View.GONE);

                                recyData.setVisibility(View.VISIBLE);

                                AssetAdapter adapter = new AssetAdapter(assetListResponse.getAssets(),AssetListActivity.this);
                                recyData.setAdapter(adapter);

                                adapter.setUp(as -> {
                                    

                                    Intent intent = new Intent(AssetListActivity.this,AssetDetailsActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("as",as);
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                });

                            }else {

                                tvNoData.setVisibility(View.VISIBLE);
                                recyData.setVisibility(View.GONE);

                            }

                            psBar.setVisibility(View.GONE);

                        });

                    }
                });

    }
}