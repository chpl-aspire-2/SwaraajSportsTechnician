package com.swaraajsports.technician.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.adapter.ChipDaynamicAdapter;
import com.swaraajsports.technician.response.CommonResponse;
import com.swaraajsports.technician.response.DashboardResponse;
import com.swaraajsports.technician.selectsociety.FilterActivity;
import com.swaraajsports.technician.selectsociety.SelectSocietyActivity;
import com.swaraajsports.technician.util.Delegate;
import com.swaraajsports.technician.util.VariableBag;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class ProfileActivity extends BaseActivityClass {


    @BindView(R.id.cir_image)
    ImageView cir_image;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_mobile)
    TextView tv_mobile;

    @BindView(R.id.tv_logout)
    TextView tv_logout;

    @BindView(R.id.ps_bar)
    ProgressBar ps_bar;

    DashboardResponse userData;

    @BindView(R.id.recyWork)
    RecyclerView recyWork;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected int getContentView() {
        return R.layout.activity_profile;//your layout
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        tv_title.setText("Profile");

        BasicFunctions.displayImageBG(ProfileActivity.this, cir_image, preferenceManager.getKeyValueString(VariableBag.EMP_PROFILE));

        tv_name.setText(preferenceManager.getUserName());

        tv_mobile.setText(preferenceManager.getKeyValueString(VariableBag.COUNTRY_CODE)+""+preferenceManager.getKeyValueString(VariableBag.USER_MOBILE));
        ps_bar.setVisibility(View.GONE);
        tv_logout.setVisibility(View.VISIBLE);

        recyWork.setHasFixedSize(true);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyWork.setLayoutManager(layoutManager);


        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            userData = (DashboardResponse) bundle.getSerializable("userData");
            if (userData!=null && userData.getCategroy()!=null){

                List<String> selectedDays = new ArrayList<>();

                for (DashboardResponse.Categroy c : userData.getCategroy()){

                    selectedDays.add(c.getCategoryName());

                }

                ChipDaynamicAdapter chipDaynamicAdapter = new ChipDaynamicAdapter(selectedDays);
                recyWork.setAdapter(chipDaynamicAdapter);

            }
        }


        tv_logout.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                ps_bar.setVisibility(View.VISIBLE);
                tv_logout.setVisibility(View.GONE);

                getCallSociety().getLogout("user_logout",preferenceManager.getUserId(),preferenceManager.getKeyValueString(VariableBag.COUNTRY_CODE),preferenceManager.getKeyValueString(VariableBag.USER_MOBILE))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<CommonResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                                showAlertDialog(e.getLocalizedMessage());
                                ps_bar.setVisibility(View.GONE);
                                tv_logout.setVisibility(View.VISIBLE);


                            }

                            @Override
                            public void onNext(CommonResponse commonResponse) {
                                new Gson().toJson(commonResponse);
                                if (commonResponse!=null){
                                    if (commonResponse.getStatus().equalsIgnoreCase("200")){
                                        BasicFunctions.toast(ProfileActivity.this,commonResponse.getMessage(),1);

                                        preferenceManager.clearPreferences();


                                        if (Delegate.mainActivity!=null && !Delegate.mainActivity.isDestroyed()){
                                            Delegate.mainActivity.finish();
                                        }

                                        if (VariableBag.WHITE_LABEL_APP){
                                            Intent intent = new Intent(ProfileActivity.this, SelectSocietyActivity.class);
                                            intent.putExtra("countryId", VariableBag.WHITE_COUNTRY_ID);
                                            intent.putExtra("stateId", "");
                                            intent.putExtra("cityId", "");
                                            intent.putExtra("isFromSetting", false);
                                            intent.putExtra("countryCode", VariableBag.WHITE_COUNTRY_CODE);
                                            startActivity(intent);

                                            finish();
                                        }else {
                                            startActivity(new Intent(ProfileActivity.this, FilterActivity.class));
                                            finish();

                                        }


                                    }else {
                                        ps_bar.setVisibility(View.GONE);
                                        tv_logout.setVisibility(View.VISIBLE);

                                        showAlertDialog(commonResponse.getMessage());
                                    }
                                }else {
                                    ps_bar.setVisibility(View.GONE);
                                    tv_logout.setVisibility(View.VISIBLE);

                                    showAlertDialog("Something went wrong!");
                                }
                            }
                        });


            }
        });


        ivBack.setOnClickListener(view -> finish());

    }

}