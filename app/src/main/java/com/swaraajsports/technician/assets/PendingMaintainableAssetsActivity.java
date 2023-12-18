package com.swaraajsports.technician.assets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.activity.BaseActivityClass;
import com.swaraajsports.technician.adapter.MissMaintainableAssetAdapter;
import com.swaraajsports.technician.response.MissingAssetResponse;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class PendingMaintainableAssetsActivity extends BaseActivityClass {

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.recyData)
    RecyclerView recyData;

    @BindView(R.id.psBar)
    ProgressBar psBar;

    @BindView(R.id.tvNoData)
    TextView tvNoData;

    ActivityResultLauncher<Intent> waitResultForPhoto;
    ArrayList<String> filePathsTemp = new ArrayList<>();

    ImageView iv_profile;

    @BindView(R.id.tv_title)
    TextView tv_title;


    @Override
    protected int getContentView() {
        return R.layout.activity_maintable_assets;//your layout
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        recyData.setLayoutManager(new LinearLayoutManager(this));


        tv_title.setText("Missed Maintenance");

        psBar.setVisibility(View.VISIBLE);
        recyData.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);


        getApiCallData();

        waitResultForPhoto= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        String onPhotoTaken;
                        if (result.getData() != null) {
                            onPhotoTaken = result.getData().getStringExtra("onPhotoTaken");
                            filePathsTemp.add(onPhotoTaken);
                            BasicFunctions.displayImageBG(PendingMaintainableAssetsActivity.this, iv_profile, filePathsTemp.get(0));

                        }

                    }
                });

        ivBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }

    private void getApiCallData() {

        getCallSociety().myMissingMaintaince("myMissingMaintaince",
                        preferenceManager.getSocietyId(),
                        preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<MissingAssetResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> {

                            psBar.setVisibility(View.GONE);
                            recyData.setVisibility(View.GONE);
                            tvNoData.setVisibility(View.VISIBLE);

                            tvNoData.setText(e.getLocalizedMessage());
                        });
                    }

                    @Override
                    public void onNext(MissingAssetResponse assetListResponse) {

                        runOnUiThread(() -> {

                            if (assetListResponse.getMaintenance()!=null && assetListResponse.getMaintenance().size()>0){

                                recyData.setVisibility(View.VISIBLE);
                                tvNoData.setVisibility(View.GONE);

                                MissMaintainableAssetAdapter assetAdapter = new MissMaintainableAssetAdapter(assetListResponse.getMaintenance(), PendingMaintainableAssetsActivity.this);
                                recyData.setAdapter(assetAdapter);

                                assetAdapter.setUp(as -> {

                                });
                                

                            }else {

                                recyData.setVisibility(View.GONE);
                                tvNoData.setVisibility(View.VISIBLE);

                                tvNoData.setText(assetListResponse.getMessage());
                            }

                            psBar.setVisibility(View.GONE);


                        });

                    }
                });

    }

}