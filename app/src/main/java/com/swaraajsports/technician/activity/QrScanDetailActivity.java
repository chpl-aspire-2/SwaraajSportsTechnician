package com.swaraajsports.technician.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.response.CommonResponse;
import com.swaraajsports.technician.response.EventListResponce;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.gson.Gson;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class QrScanDetailActivity extends BaseActivityClass {

    @BindView(R.id.fram_cam)
    FrameLayout frameLayout;

    @BindView(R.id.codeScannerView)
    CodeScannerView scannerView;
    private CodeScanner mCodeScanner;

    EventListResponce.EventList eventList;


    @Override
    protected int getContentView() {
        return R.layout.activity_qr_scan_detail;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            this.setTurnScreenOn(true);
        } else {
            final Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }

        System.gc();

        Bundle  bundle = getIntent().getExtras();
        if (bundle!=null){

            eventList = (EventListResponce.EventList) bundle.getSerializable("eventList");
            initCode();
        }


    }


    @Override
    public void onResume() {
        super.onResume();

        if (mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    public void onPause() {
        if (mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }
    private void initCode() {

        if (mCodeScanner == null) {
            mCodeScanner = new CodeScanner(this, scannerView);
        }
        mCodeScanner.startPreview();
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {

            if (result.getText().contains("_")) {

                String[] separated = result.getText().split("_");


                if (separated[0].equalsIgnoreCase("Asn")) {

                    if (separated[1] != null) {

                        frameLayout.setVisibility(View.GONE);

                        callApi(separated[1]);
                    }
                } else {
                    showAlertDialog("Invalid QR Code!");
                }
            } else {
                showAlertDialog("Invalid QR Code!");
            }



        }));
    }

    private void callApi(String s) {

        getCallSociety().scannerDataDetail("passDetail",
                s,preferenceManager.getSocietyId(),preferenceManager.getUserId(),"1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showAlertDialog("No Internet Connection!");

                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {


                        new Gson().toJson(commonResponse);

                        if (commonResponse!=null){

                            if (commonResponse.getStatus().equalsIgnoreCase("200")){

                                BasicFunctions.toast(QrScanDetailActivity.this,commonResponse.getMessage(),1);


                                 Dialog dialog = new Dialog(QrScanDetailActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.show_qr_detail);

                                Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
                                dialogButton.setOnClickListener(v -> dialog.dismiss());

                                dialog.show();



                            }else {
                                showAlertDialog(commonResponse.getMessage());

                            }

                        }else {

                            showAlertDialog("Something went wrong! Try again.");
                        }

                    }
                });

    }
}