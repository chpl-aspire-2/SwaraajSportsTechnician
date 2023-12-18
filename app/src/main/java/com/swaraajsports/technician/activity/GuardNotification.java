package com.swaraajsports.technician.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.adapter.GuardNotificationAdapter;
import com.swaraajsports.technician.response.CommonResponse;
import com.swaraajsports.technician.response.NotificationResponce;
import com.swaraajsports.technician.util.PreferenceManager;

import java.util.Objects;

import butterknife.BindView;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class GuardNotification extends BaseActivityClass {

    @BindView(R.id.guardnotification)
    RecyclerView recyclerView;

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @BindView(R.id.ps_bar)
    ProgressBar ps_bar;
    @BindView(R.id.tvNodataAvailable)
    TextView tvNodataAvailable;


    @BindView(R.id.lin_nodata)
    LinearLayout tv_no_data;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.ivDelete)
    ImageView ivDelete;

    @BindView(R.id.tv_title)
    TextView tv_title;


    PreferenceManager preferenceManager;
    private GuardNotificationAdapter guardNotificationAdapter;


    @Override
    protected int getContentView() {
        return R.layout.activity_guard_notification;//your layout
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            this.setTurnScreenOn(true);
        } else {
            final Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }

        preferenceManager = new PreferenceManager(this);
        System.gc();

        tv_title.setText("Notification");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setVisibility(View.GONE);
        ps_bar.setVisibility(View.VISIBLE);
        tv_no_data.setVisibility(View.GONE);

        initCode();

        swipe.setOnRefreshListener(() -> {

            swipe.setRefreshing(true);
            initCode();

            new Handler().postDelayed(() -> swipe.setRefreshing(false), 2500);
        });


        ivBack.setOnClickListener(view -> finish());
        ivDelete.setOnClickListener(view -> runOnUiThread(() -> {

            if (!isFinishing()){
                new AlertDialog.Builder(GuardNotification.this)
                        .setMessage("Are you sure to delete all notification?")
                        .setCancelable(false)
                        .setNegativeButton("no", (dialog, which) -> dialog.dismiss())
                        .setPositiveButton("yes", (dialog, which) -> {

                            dialog.dismiss();
                            callDeleteAll();
                        }).show();
            }
        }));

    }

    private void callDeleteAll() {

        getCallSociety().removeAllNotification("DeleteUserNotificationAll", "1",
                preferenceManager.getUserId(), "1")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> BasicFunctions.toast(GuardNotification.this, "No Internet Connection", 3));
                    }

                    @Override
                    public void onNext(CommonResponse commenResponce) {
                        runOnUiThread(() -> {
                            initCode();
                            BasicFunctions.toast(GuardNotification.this, commenResponce.getMessage(), 3);
                        });


                    }
                });


    }

    private void initCode() {


        getCallSociety().getNotification("getNotification", preferenceManager.getUserId(), "1", "1", preferenceManager.getSocietyId(), "1")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<NotificationResponce>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {

                        runOnUiThread(() ->{ Log.e("@@", Objects.requireNonNull(e.getMessage()));
                            BasicFunctions.toast(GuardNotification.this, "No Internet Connection", 3);
                        });

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(final NotificationResponce notificationResponce) {


                        runOnUiThread(() -> {

                            if (notificationResponce.getStatus() != null && notificationResponce.getStatus().equalsIgnoreCase("200")) {

                                recyclerView.setVisibility(View.VISIBLE);
                                ps_bar.setVisibility(View.GONE);
                                tv_no_data.setVisibility(View.GONE);

                                guardNotificationAdapter = new GuardNotificationAdapter(notificationResponce, GuardNotification.this);

                                guardNotificationAdapter.setUpInterface(new GuardNotificationAdapter.NotificatioInterFace() {
                                    @Override
                                    public void deleteClick(int pos, String data) {
                                        deletenoti(data);
                                    }

                                    @Override
                                    public void NotificationClick(NotificationResponce.Notification notification) {
                                        if (notification.getClick_action() != null) {

                                            Intent intent;
                                            if (notification.getClick_action() != null) {
                                                if (notification.getClick_action().equalsIgnoreCase("custom_notification")) {
                                                    intent = new Intent(GuardNotification.this, CustomNotificationActivity.class);
                                                    intent.putExtra("img", notification.getNotificationLogo());
                                                    intent.putExtra("title", notification.getGuardNotificationTitle());
                                                    intent.putExtra("desc", notification.getGuardNotificationDesc());
                                                    intent.putExtra("time", notification.getGuardNotificationDate());

                                                } else {
                                                    intent = new Intent(GuardNotification.this, ComplainActivity.class);
                                                    intent.putExtra("status", notification.getClick_action() + "");
                                                }
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                });

                                recyclerView.setAdapter(guardNotificationAdapter);

                            } else {

                                recyclerView.setVisibility(View.GONE);
                                ps_bar.setVisibility(View.GONE);
                                tv_no_data.setVisibility(View.VISIBLE);
                                tvNodataAvailable.setText("No Data");
                            }

                        });

                    }
                });


    }

    public void deletenoti(String id) {

        getCallSociety().removeNotification("DeleteUserNotification", id, "1", preferenceManager.getUserId(), "1")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> BasicFunctions.toast(GuardNotification.this, "No Internet Connection", 3));

                    }

                    @Override
                    public void onNext(CommonResponse commenResponce) {
                        runOnUiThread(() -> {
                            BasicFunctions.toast(GuardNotification.this, commenResponce.getMessage(), 3);
                            initCode();
                        });

                    }
                });


    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
