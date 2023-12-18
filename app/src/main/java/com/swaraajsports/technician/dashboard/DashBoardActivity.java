package com.swaraajsports.technician.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.ajitmaurya.basicsetup.askPermission.PermissionHandler;
import com.ajitmaurya.basicsetup.askPermission.Permissions;
import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.ajitmaurya.basicsetup.view.CustomEditText;
import com.swaraajsports.technician.BuildConfig;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.activity.BaseActivityClass;
import com.swaraajsports.technician.activity.ClickImageActivity;
import com.swaraajsports.technician.activity.ComplainActivity;
import com.swaraajsports.technician.activity.ComplainDetailsActivity;
import com.swaraajsports.technician.activity.GuardNotification;
import com.swaraajsports.technician.activity.ProfileActivity;
import com.swaraajsports.technician.adapter.ComplainAdapter;
import com.swaraajsports.technician.adapter.MaintainableAssetAdapter;
import com.swaraajsports.technician.assets.AssetListActivity;
import com.swaraajsports.technician.assets.CompletedMaintainableAssetsActivity;
import com.swaraajsports.technician.assets.MaintainableAssetsActivity;
import com.swaraajsports.technician.assets.PendingMaintainableAssetsActivity;
import com.swaraajsports.technician.attendance.AttendanceActivity;
import com.swaraajsports.technician.response.AssetDetailResponse;
import com.swaraajsports.technician.response.CommonResponse;
import com.swaraajsports.technician.response.ComplainResponse;
import com.swaraajsports.technician.response.DashboardResponse;
import com.swaraajsports.technician.response.VersionResponce;
import com.swaraajsports.technician.selectsociety.FilterActivity;
import com.swaraajsports.technician.selectsociety.SelectSocietyActivity;
import com.swaraajsports.technician.util.CirclePagerIndicatorDecoration;
import com.swaraajsports.technician.util.Delegate;
import com.swaraajsports.technician.util.PickFileActivity;
import com.swaraajsports.technician.util.VariableBag;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.joon.notificationtimer.NotificationTimer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class DashBoardActivity extends BaseActivityClass {


    @BindView(R.id.cir_profile)
    CircleImageView cirProfile;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rel_noti)
    RelativeLayout relNoti;
    @BindView(R.id.imgNoti)
    ImageView imgNoti;
    @BindView(R.id.tv_noti_count)
    TextView tvNotiCount;
    @BindView(R.id.netstedData)
    NestedScrollView netstedData;
    @BindView(R.id.linLayGetParcels)
    LinearLayout linLayGetParcels;
    @BindView(R.id.cardMenuGetParcels)
    CardView cardMenuGetParcels;
    @BindView(R.id.tvGetParcels)
    TextView tvGetParcels;
    @BindView(R.id.tv_open_stat)
    TextView tvOpenStat;
    @BindView(R.id.tv_close_stat)
    TextView tvCloseStat;
    @BindView(R.id.tv_reopen_stat)
    TextView tvReopenStat;
    @BindView(R.id.tv_hold_stat)
    TextView tv_hold_stat;
    @BindView(R.id.iv_inprogress_stat)
    TextView ivInprogressStat;
    @BindView(R.id.linDataCurrent)
    LinearLayout linDataCurrent;
    @BindView(R.id.tv_title_no)
    TextView tvTitleNo;
    @BindView(R.id.recy_data)
    RecyclerView recyData;
    @BindView(R.id.ps_bar2)
    ProgressBar psBar2;
    @BindView(R.id.tv_title_ipc)
    TextView tv_title_ipc;

    @BindView(R.id.linInprogress)
    LinearLayout linInprogress;

    @BindView(R.id.linReopen)
    LinearLayout linReopen;

    @BindView(R.id.linClose)
    LinearLayout linClose;

    @BindView(R.id.linOpen)
    LinearLayout linOpen;

    @BindView(R.id.linHold)
    LinearLayout linHold;


    @BindView(R.id.linLayAsset)
    LinearLayout linLayAsset;
    @BindView(R.id.cardMenuAsset)
    CardView cardMenuAsset;
    @BindView(R.id.tvAssetTitle)
    TextView tvAssetTitle;
    @BindView(R.id.tv_title_today_assets)
    TextView tvTitleTodayAssets;
    @BindView(R.id.relViewAll)
    LinearLayout relViewAll;
    @BindView(R.id.relViewAttendAll)
    TextView relViewAttendAll;

    @BindView(R.id.recyAssetsToday)
    RecyclerView recyAssetsToday;

    @BindView(R.id.tvViewAll)
    TextView tvViewAll;

    @BindView(R.id.tvMainAll)
    TextView tvMainAll;
    @BindView(R.id.tvComAll)
    TextView tvComAll;
    @BindView(R.id.tvPenAll)
    TextView tvPenAll;

    @BindView(R.id.tvWeek)
    TextView tvWeek;
    @BindView(R.id.tvMonth)
    TextView tvMonth;


    int sizeListC = 0;
    int sizeListA = 0;

    @BindView(R.id.tv_page_com)
    TextView tv_page_com;

    @BindView(R.id.tv_assets_count)
    TextView tv_assets_count;

    @BindView(R.id.tvMonthlyName)
    TextView tvMonthlyName;

    boolean doubleBackToExitPressedOnce = false;
    NotificationTimer.Builder notifire;

    String currentComId = "0";

    ActivityResultLauncher<Intent> waitResultForScanResult;
    DashboardResponse userData;
    String m_androidId;

    ComplainAdapter complainAdapter;
    TextView tv_bill_pay_file;


    ActivityResultLauncher<Intent> waitResultForPhoto, waitResultForFile, waitResultForFilePhoto;
    ArrayList<String> filePathsTemp = new ArrayList<>();

    MultipartBody.Part fileToUpload = null;
    MultipartBody.Part fileToUploadPhoto = null;
    ImageView iv_profile;
    private boolean flgPic = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_dash_board;//your layout
    }

    @SuppressLint({"SetTextI18n", "HardwareIds"})
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent2) {
        super.onViewReady(savedInstanceState, intent2);


        waitResultForScanResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> getDashBoardData());

        Delegate.mainActivity = this;


        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getBoolean("isFromFCM")) {
            String cliAc = bundle.getString("click_action");
            if (cliAc.length() < 3) {
                Intent intent = new Intent(DashBoardActivity.this, ComplainActivity.class);
                intent.putExtra("status", cliAc + "");
                waitResultForScanResult.launch(intent);
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DashBoardActivity.this, RecyclerView.HORIZONTAL, false);
        recyData.setLayoutManager(linearLayoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyData);

        recyData.addItemDecoration(new CirclePagerIndicatorDecoration());

        recyData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    tv_page_com.setText((position + 1) + "/" + sizeListC);

                }
            }
        });


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(DashBoardActivity.this, RecyclerView.HORIZONTAL, false);
        recyAssetsToday.setLayoutManager(linearLayoutManager2);
        SnapHelper snapHelper2 = new PagerSnapHelper();
        snapHelper2.attachToRecyclerView(recyAssetsToday);


        recyAssetsToday.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    int position = linearLayoutManager2.findFirstVisibleItemPosition();
                    tv_assets_count.setText((position + 1) + "/" + sizeListA);

                }
            }
        });


        waitResultForPhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {

                String onPhotoTaken;
                if (result.getData() != null) {
                    onPhotoTaken = result.getData().getStringExtra("onPhotoTaken");
                    filePathsTemp.add(onPhotoTaken);
                    flgPic = true;
                    BasicFunctions.displayImageBG(DashBoardActivity.this, iv_profile, filePathsTemp.get(0));

                }

            }
        });


        waitResultForFilePhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {

                String onPhotoTaken;
                if (result.getData() != null) {
                    onPhotoTaken = result.getData().getStringExtra("onPhotoTaken");

                    File file = new File(BasicFunctions.compressImage(DashBoardActivity.this, onPhotoTaken));
                    RequestBody requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                    fileToUpload = MultipartBody.Part.createFormData("maintenance_invoice", file.getName(), requestBody);

                }

            }
        });

        waitResultForFile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                if (result.getData() != null) {
                    fileToUpload = VariableBag.partsFilePick;
                    if (tv_bill_pay_file != null) {
                        tv_bill_pay_file.setText(result.getData().getStringExtra("filePath"));
                    }
                }
            }
        });


        checkForUpdate();


        initCode();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Permissions.checkAsk(DashBoardActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, getString(R.string.notification_per), null, new PermissionHandler() {
                @Override
                public void onGranted() {

                }
            });
        }
    }

    private void checkForUpdate() {
        @SuppressLint("SimpleDateFormat") java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        String todayDate = sdf.format(c.getTime()) + "";
        if (!todayDate.equalsIgnoreCase(preferenceManager.getKeyValueString("versionCall"))) {
            preferenceManager.setKeyValueString("versionCall", todayDate);

            final int versionCode = BuildConfig.VERSION_CODE;

            callMainUrl.getVersion("getVersion", "5", "1").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<VersionResponce>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(VersionResponce versionResponce) {

                    runOnUiThread(() -> {
                        if (versionResponce.getStatus().equalsIgnoreCase("200")) {

                            int versionCodeServer = Integer.parseInt(versionResponce.getVersion_name());

                            if (versionCodeServer > versionCode) {
                                updateDialog(versionResponce.getVersion_name());
                            }
                        }

                    });
                }
            });
        }
    }


    private void updateDialog(String version) {
        final Dialog appUpdateDialog = new Dialog(this);
        appUpdateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(appUpdateDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        appUpdateDialog.setCancelable(false);
        appUpdateDialog.setContentView(R.layout.dialog_app_update);

        Button btnUpdate = appUpdateDialog.findViewById(R.id.btnUpdate);
        TextView tvVersion = appUpdateDialog.findViewById(R.id.tvVersion);

        tvVersion.setText(version);

        btnUpdate.setOnClickListener(v -> {
            DashBoardActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + DashBoardActivity.this.getPackageName())));
            // appUpdateDialog.cancel();
        });
        appUpdateDialog.show();
    }


    @SuppressLint({"HardwareIds", "SetTextI18n"})
    public void initCode() {
        runOnUiThread(() -> {
            if (preferenceManager.getLoginSession()) {

                recyData.setVisibility(View.GONE);
                tvTitleNo.setVisibility(View.GONE);
                psBar2.setVisibility(View.GONE);
                tv_title_ipc.setVisibility(View.GONE);
                linDataCurrent.setVisibility(View.VISIBLE);

                m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                BasicFunctions.displayImage(DashBoardActivity.this, cirProfile, preferenceManager.getKeyValueString(VariableBag.EMP_PROFILE), R.drawable.user, R.drawable.user);

                getDashBoardData();

                tvTitle.setText("Welcome " + preferenceManager.getUserName());

                cirProfile.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {

                        Intent intent = new Intent(DashBoardActivity.this, ProfileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", userData);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });

                tvTitle.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {

                        Intent intent = new Intent(DashBoardActivity.this, ProfileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", userData);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                relNoti.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {

                        Intent intent = new Intent(DashBoardActivity.this, GuardNotification.class);
                        waitResultForScanResult.launch(intent);

                    }
                });


                tvViewAll.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {

                        startActivity(new Intent(DashBoardActivity.this, AssetListActivity.class));


                    }
                });

                tvMainAll.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {

                        startActivity(new Intent(DashBoardActivity.this, MaintainableAssetsActivity.class));


                    }
                });

                tvPenAll.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {

                        startActivity(new Intent(DashBoardActivity.this, PendingMaintainableAssetsActivity.class));


                    }
                });
                tvComAll.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {

                        startActivity(new Intent(DashBoardActivity.this, CompletedMaintainableAssetsActivity.class));


                    }
                });

                relViewAttendAll.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {


                        startActivity(new Intent(DashBoardActivity.this, AttendanceActivity.class));


                    }
                });

            } else {

                if (VariableBag.WHITE_LABEL_APP) {
                    Intent intent = new Intent(DashBoardActivity.this, SelectSocietyActivity.class);
                    intent.putExtra("countryId", VariableBag.WHITE_COUNTRY_ID);
                    intent.putExtra("stateId", "");
                    intent.putExtra("cityId", "");
                    intent.putExtra("isFromSetting", false);
                    intent.putExtra("countryCode", VariableBag.WHITE_COUNTRY_CODE);
                    startActivity(intent);
                    finish();
                } else {
                    startActivity(new Intent(DashBoardActivity.this, FilterActivity.class));
                    finish();
                }


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDashBoardData();
    }

    public void getDashBoardData() {

        runOnUiThread(() -> getCallSociety().getDashboardData("getDashboardData", preferenceManager.getUserId(), preferenceManager.getSocietyId(), m_androidId, "1", preferenceManager.getKeyValueString("token"), preferenceManager.getKeyValueString(VariableBag.COUNTRY_CODE), "android", BuildConfig.VERSION_CODE + "", Build.BRAND, Build.MODEL, preferenceManager.getKeyValueString(VariableBag.USER_MOBILE)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<DashboardResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                BasicFunctions.toast(DashBoardActivity.this, "No Internet Connection", 1);

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onNext(DashboardResponse dashboardResponse) {


                new Gson().toJson(dashboardResponse);

                if (dashboardResponse != null) {

                    userData = dashboardResponse;
                    if (dashboardResponse.getStatus().equalsIgnoreCase("200")) {


                        if (dashboardResponse.getThis_week_present() != null) {

                            tvWeek.setText(dashboardResponse.getThis_week_present() + " out of 7 days");
                            tvMonth.setText(dashboardResponse.getThis_month_present() + " out of " + dashboardResponse.getTotal_day_in_month() + " days");
                            tvMonthlyName.setText(dashboardResponse.getThis_month_name() + "");

                        }


                        preferenceManager.setUserFullName(dashboardResponse.getEmp_name());
                        preferenceManager.setKeyValueString(VariableBag.EMP_JOINING_DATE, dashboardResponse.getEmp_date_of_joing());
                        preferenceManager.setKeyValueString(VariableBag.EMP_ADDRESS, dashboardResponse.getEmp_address());
                        preferenceManager.setKeyValueString(VariableBag.EMP_EMAIL, dashboardResponse.getEmp_email());
                        preferenceManager.setKeyValueString(VariableBag.EMP_SALLARY, dashboardResponse.getEmp_sallary());
                        preferenceManager.setKeyValueString(VariableBag.EMP_PROFILE, dashboardResponse.getEmp_profile());
                        //     BasicFunctions.displayImageBG(MainActivity.this, cir_profile, preferenceManager.getKeyValueString(VariableBag.EMP_PROFILE));


                        tvOpenStat.setText("" + dashboardResponse.getOpen_complain());
                        tvCloseStat.setText("" + dashboardResponse.getClose_complain());
                        tvReopenStat.setText("" + dashboardResponse.getRe_pen_complain());
                        ivInprogressStat.setText("" + dashboardResponse.getInprogress_complain());
                        tv_hold_stat.setText("" + dashboardResponse.getHold_complain());


                        linOpen.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {

                                Intent intent = new Intent(DashBoardActivity.this, ComplainActivity.class);
                                intent.putExtra("status", dashboardResponse.getOpen_status() + "");
                                waitResultForScanResult.launch(intent);

                            }
                        });


                        linClose.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {

                                Intent intent = new Intent(DashBoardActivity.this, ComplainActivity.class);
                                intent.putExtra("status", dashboardResponse.getClose_status() + "");
                                waitResultForScanResult.launch(intent);

                            }
                        });


                        linReopen.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {

                                Intent intent = new Intent(DashBoardActivity.this, ComplainActivity.class);
                                intent.putExtra("status", dashboardResponse.getReopen_status() + "");
                                waitResultForScanResult.launch(intent);

                            }
                        });


                        linInprogress.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {

                                Intent intent = new Intent(DashBoardActivity.this, ComplainActivity.class);
                                intent.putExtra("status", dashboardResponse.getInprogress_status() + "");
                                waitResultForScanResult.launch(intent);

                            }
                        });

                        linHold.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {

                                Intent intent = new Intent(DashBoardActivity.this, ComplainActivity.class);
                                intent.putExtra("status", dashboardResponse.getHold_status() + "");
                                waitResultForScanResult.launch(intent);

                            }
                        });

                        if (dashboardResponse.getReadStatus() != null) {
                            if (dashboardResponse.getReadStatus().equalsIgnoreCase("0")) {
                                tvNotiCount.setVisibility(View.GONE);

                            } else {
                                tvNotiCount.setVisibility(View.VISIBLE);
                                tvNotiCount.setText(dashboardResponse.getReadStatus() + "");
                            }
                        }

                        if (dashboardResponse.getComplain() != null && dashboardResponse.getComplain().size() > 0) {

                            recyData.setVisibility(View.VISIBLE);
                            tvTitleNo.setVisibility(View.GONE);
                            tv_title_ipc.setVisibility(View.VISIBLE);
                            psBar2.setVisibility(View.GONE);

                            boolean isFound = false;

                            for (ComplainResponse.Complain co : dashboardResponse.getComplain()) {

                                if (currentComId.equalsIgnoreCase(co.getComplain_id())) {
                                    isFound = true;
                                    break;
                                }

                            }


                            if (!isFound) {
                                if (notifire != null) {
                                    notifire.terminate();
                                }
                            }

                            tv_page_com.setText("1/" + dashboardResponse.getComplain().size());

                            sizeListC = dashboardResponse.getComplain().size();

                            complainAdapter = new ComplainAdapter(DashBoardActivity.this, dashboardResponse.getComplain());
                            recyData.setAdapter(complainAdapter);


                            complainAdapter.setOnClickListener(new ComplainAdapter.SetOnClickListener() {
                                @Override
                                public void onClick(int position, ComplainResponse.Complain complain) {

                                    Intent intent = new Intent(DashBoardActivity.this, ComplainDetailsActivity.class);
                                    intent.putExtra("complainId", complain.getComplain_id());
                                    intent.putExtra("complainTitle", complain.getCompalain_title());
                                    intent.putExtra("complainNo", complain.getComplain_no());
                                    intent.putExtra("complainDesc", complain.getComplain_description());
                                    intent.putExtra("complainCatId", complain.getComplaint_category());
                                    intent.putExtra("complainCatView", complain.getComplaint_category_view());
                                    intent.putExtra("isBlocked", false);
                                    intent.putExtra("blockMsg", "");
                                    intent.putExtra("ComplainStatus", complain.getComplain_status());
                                    intent.putExtra("userId", complain.getUser_id());
                                    startActivity(intent);

                                }

                                @Override
                                public void onDelete(int position, ComplainResponse.Complain complain) {

                                }

                                @Override
                                public void onRateClick(int position, ComplainResponse.Complain complain) {

                                }

                                @Override
                                public void setHold(ComplainResponse.Complain complain) {


                                    final Dialog dialog = new Dialog(DashBoardActivity.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.dialog_reason_hold);

                                    EditText etComments = (EditText) dialog.findViewById(R.id.etReason);

                                    Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
                                    Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                                    dialogButton.setOnClickListener(v -> {
                                        if (etComments.getText().toString().trim().length() > 0) {
                                            dialog.dismiss();
                                            psBar2.setVisibility(View.VISIBLE);
                                            recyData.setVisibility(View.GONE);
                                            tvTitleNo.setVisibility(View.GONE);
                                            currentComId = complain.getComplain_id();
                                            callClose(complain.getComplain_id(), true, etComments.getText().toString());
                                        } else {
                                            etComments.setError("Please enter reason");
                                            etComments.requestFocus();

                                        }
                                    });

                                    btn_cancel.setOnClickListener(new OnSingleClickListener() {
                                        @Override
                                        public void onSingleClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();
                                    etComments.requestFocus();
                                }

                                @Override
                                public void setInProgress(ComplainResponse.Complain complain) {
                                    psBar2.setVisibility(View.VISIBLE);
                                    recyData.setVisibility(View.GONE);
                                    tvTitleNo.setVisibility(View.GONE);

                                    callInProgress(complain);

                                }

                                @Override
                                public void setClose(ComplainResponse.Complain complain) {
                                    psBar2.setVisibility(View.VISIBLE);
                                    recyData.setVisibility(View.GONE);
                                    tvTitleNo.setVisibility(View.GONE);
                                    currentComId = complain.getComplain_id();
                                    callClose(complain.getComplain_id(), false, "");

                                }

                                @Override
                                public void setReqClose(ComplainResponse.Complain complain) {
                                    psBar2.setVisibility(View.VISIBLE);
                                    recyData.setVisibility(View.GONE);
                                    tvTitleNo.setVisibility(View.GONE);
                                    currentComId = complain.getComplain_id();
                                    callRequestClose(complain);

                                }
                            });

                        } else {

                            if (notifire != null) {
                                notifire.terminate();
                            }

                            recyData.setVisibility(View.GONE);
                            tvTitleNo.setVisibility(View.VISIBLE);
                            psBar2.setVisibility(View.GONE);
                            tv_title_ipc.setVisibility(View.GONE);
                        }


                        if (dashboardResponse.getMaintenance() != null && dashboardResponse.getMaintenance().size() > 0) {

                            recyAssetsToday.addItemDecoration(new CirclePagerIndicatorDecoration());

                            tvTitleTodayAssets.setVisibility(View.VISIBLE);
                            tv_assets_count.setVisibility(View.VISIBLE);
                            recyAssetsToday.setVisibility(View.VISIBLE);
                            MaintainableAssetAdapter assetAdapter = new MaintainableAssetAdapter(dashboardResponse.getMaintenance(), DashBoardActivity.this);
                            recyAssetsToday.setAdapter(assetAdapter);
                            sizeListA = dashboardResponse.getMaintenance().size();
                            tv_assets_count.setText("1/" + sizeListA);

                            assetAdapter.setUp(new MaintainableAssetAdapter.ClickRow() {
                                @Override
                                public void click(AssetDetailResponse.Maintenance as, AssetDetailResponse.Schedule scdule) {


                                    fileToUpload = null;
                                    final Dialog dialog = new Dialog(DashBoardActivity.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.submit_comple_work);

                                    CustomEditText etAmount = dialog.findViewById(R.id.etNoofUnitsPrv);
                                    CustomEditText etRemark = dialog.findViewById(R.id.etNoofUnits);
                                    iv_profile = dialog.findViewById(R.id.iv_profile);


                                    tv_bill_pay_file = dialog.findViewById(R.id.tv_bill_pay_file);
                                    ImageView tv_bill_pay_amount = dialog.findViewById(R.id.tv_bill_pay_amount);

                                    tv_bill_pay_amount.setOnClickListener(new OnSingleClickListener() {
                                        @Override
                                        public void onSingleClick(View v) {

                                            StrictMode.VmPolicy.Builder builder2 = new StrictMode.VmPolicy.Builder();
                                            StrictMode.setVmPolicy(builder2.build());

                                            final CharSequence[] options = {"Take Photo", "Choose From File", "Cancel"};
                                            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DashBoardActivity.this);
                                            builder.setTitle("Select Option");
                                            builder.setItems(options, (dialog2, item) -> {
                                                if (options[item].equals("Take Photo")) {

                                                    Permissions.check(DashBoardActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, getString(R.string.camera_storege_per), null, new PermissionHandler() {
                                                        @Override
                                                        public void onGranted() {

                                                            VariableBag.partsFilePick = null;

                                                            dialog2.dismiss();
                                                            Intent i = new Intent(DashBoardActivity.this, ClickImageActivity.class);
                                                            i.putExtra("picCount", 1);
                                                            i.putExtra("isGallery", false);
                                                            waitResultForFilePhoto.launch(i);


                                                        }
                                                    });

                                                } else if (options[item].equals("Choose From File")) {


                                                    Permissions.check(DashBoardActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, getString(R.string.storege_per), null, new PermissionHandler() {
                                                        @Override
                                                        public void onGranted() {
                                                            VariableBag.partsFilePick = null;

                                                            dialog2.dismiss();
                                                            Intent intent = new Intent(DashBoardActivity.this, PickFileActivity.class);
                                                            intent.putExtra("partValue", "maintenance_invoice");
                                                            waitResultForFile.launch(intent);


                                                        }
                                                    });

                                                } else if (options[item].equals("Cancel")) {

                                                    dialog2.dismiss();
                                                }
                                            });
                                            builder.show();


                                        }
                                    });


                                    iv_profile.setOnClickListener(new OnSingleClickListener() {
                                        @Override
                                        public void onSingleClick(View v) {

                                            flgPic = false;
                                            iv_profile.setImageResource(R.drawable.addphotos);

                                            Intent i = new Intent(DashBoardActivity.this, ClickImageActivity.class);
                                            i.putExtra("picCount", 1);
                                            i.putExtra("isGallery", false);
                                            waitResultForPhoto.launch(i);

                                        }
                                    });


                                    Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
                                    Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

                                    btnCancel.setOnClickListener(new OnSingleClickListener() {
                                        @Override
                                        public void onSingleClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (flgPic) {

                                                if (filePathsTemp.size() > 0) {
                                                    File file = new File(BasicFunctions.compressImage(DashBoardActivity.this, filePathsTemp.get(0)));
                                                    RequestBody requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                                                    fileToUploadPhoto = MultipartBody.Part.createFormData("maintenance_photo", file.getName(), requestBody);


                                                    RequestBody tag = RequestBody.create("assetsMaintenanceCompleted", MediaType.parse("text/plain"));
                                                    RequestBody societyId = RequestBody.create(preferenceManager.getSocietyId(), MediaType.parse("text/plain"));
                                                    RequestBody assetId = RequestBody.create(as.getAssetsId(), MediaType.parse("text/plain"));
                                                    RequestBody assets_category_id = RequestBody.create(scdule.getAssetsCategoryId(), MediaType.parse("text/plain"));
                                                    RequestBody empId = RequestBody.create(preferenceManager.getUserId(), MediaType.parse("text/plain"));
                                                    RequestBody assetMainId = RequestBody.create(scdule.getAssetsMaintenanceId(), MediaType.parse("text/plain"));
                                                    RequestBody maintDate = RequestBody.create(scdule.getMaintenanceDate(), MediaType.parse("text/plain"));
                                                    RequestBody maintAmount = RequestBody.create(etAmount.getText().toString(), MediaType.parse("text/plain"));
                                                    RequestBody remark = RequestBody.create(etRemark.getText().toString(), MediaType.parse("text/plain"));
                                                    RequestBody completed_by = RequestBody.create(preferenceManager.getUserName(), MediaType.parse("text/plain"));


                                                    getCallSociety().assetsMaintenanceCompleted(tag, societyId, assetId, assets_category_id, empId, assetMainId, maintDate, maintAmount, remark, completed_by, fileToUploadPhoto, fileToUpload).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribe(new Subscriber<CommonResponse>() {
                                                        @Override
                                                        public void onCompleted() {

                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    dialog.dismiss();
                                                                }
                                                            });

                                                        }

                                                        @Override
                                                        public void onNext(CommonResponse commonResponse) {

                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    dialog.dismiss();
                                                                    getDashBoardData();
                                                                }
                                                            });

                                                        }
                                                    });


                                                } else {
                                                    BasicFunctions.toast(DashBoardActivity.this, "Capture image of work", 1);

                                                }


                                            } else {

                                                BasicFunctions.toast(DashBoardActivity.this, "Capture image of work", 1);

                                            }


                                        }
                                    });

                                    dialog.show();


                                }
                            });

                        } else {
                            tvTitleTodayAssets.setVisibility(View.GONE);
                            tv_assets_count.setVisibility(View.GONE);
                            recyAssetsToday.setVisibility(View.GONE);

                        }


                    } else {

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DashBoardActivity.this);
                        dialogBuilder.setIcon(R.mipmap.ic_launcher);
                        dialogBuilder.setMessage(dashboardResponse.getMessage());
                        dialogBuilder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                            preferenceManager.clearPreferences();
                            dialog.cancel();
                            if (VariableBag.WHITE_LABEL_APP) {
                                Intent intent = new Intent(DashBoardActivity.this, SelectSocietyActivity.class);
                                intent.putExtra("countryId", VariableBag.WHITE_COUNTRY_ID);
                                intent.putExtra("stateId", "");
                                intent.putExtra("cityId", "");
                                intent.putExtra("isFromSetting", false);
                                intent.putExtra("countryCode", VariableBag.WHITE_COUNTRY_CODE);
                                startActivity(intent);
                                finish();
                            } else {
                                startActivity(new Intent(DashBoardActivity.this, FilterActivity.class));
                                finish();
                            }
                        });
                        dialogBuilder.setCancelable(false);
                        dialogBuilder.show();

                    }


                }


            }
        }));


    }

    private void callInProgress(ComplainResponse.Complain complain) {


        getCallSociety().inProgressComplain("inProgressComplain", preferenceManager.getSocietyId(), preferenceManager.getUserId(), complain.getComplain_id(), "1").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<CommonResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                BasicFunctions.toast(DashBoardActivity.this, e.getLocalizedMessage(), 1);

            }

            @Override
            public void onNext(CommonResponse commonResponse) {
                getDashBoardData();
            }
        });

    }

    private void callRequestClose(ComplainResponse.Complain complain) {

        getCallSociety().closeComplainRequest("closeComplainRequest", preferenceManager.getSocietyId(), preferenceManager.getUserId(), preferenceManager.getUserName(), complain.getComplain_id(), "1").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<CommonResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                BasicFunctions.toast(DashBoardActivity.this, e.getLocalizedMessage(), 1);
            }

            @Override
            public void onNext(CommonResponse commonResponse) {

                getDashBoardData();

                startAutoClose(complain.getComplain_id(), complain.getComplain_no());

            }
        });
    }

    private void startAutoClose(String complain_id, String complain_no) {

        int timeDu;
        try {
            timeDu = Integer.parseInt(userData.getAuto_close_time()) * 60000;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            timeDu = 600000;
        }


        // tv_title_ip.setVisibility(View.VISIBLE);

        notifire = new NotificationTimer.Builder(DashBoardActivity.this).setSmallIcon(R.drawable.logo).setControlMode(false).setColor(R.color.colorPrimary).setShowWhen(false).setAutoCancel(false).setOnlyAlertOnce(true).setPriority(NotificationCompat.PRIORITY_LOW).setOnTickListener(new Function1<Long, Unit>() {
            @SuppressLint("SetTextI18n")
            @Override
            public Unit invoke(Long aLong) {

                // String dateString = DateFormat.format("H:M", new Date(aLong)).toString();
                // tv_title_ip.setText(complain_no+" auto close in "+dateString);
                return null;
            }
        }).setOnFinishListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {

                callClose(complain_id, false, "");

                return null;
            }
        }).setContentTitle(complain_no + " auto complete in ");

        notifire.play(timeDu);
    }


    private void callClose(String complainId, boolean isHold, String holdMsg) {

        getCallSociety().closeComplainAuto("closeComplainAuto", preferenceManager.getSocietyId(), preferenceManager.getUserId(), complainId, isHold ? "1" : "", holdMsg, "1").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<CommonResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                BasicFunctions.toast(DashBoardActivity.this, "No Internet Connection", 3);
            }

            @Override
            public void onNext(CommonResponse commonResponse) {

                getDashBoardData();
                if (notifire != null) {
                    notifire.terminate();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }
}