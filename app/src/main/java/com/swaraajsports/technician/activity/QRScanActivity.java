package com.swaraajsports.technician.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.response.CommonResponse;
import com.swaraajsports.technician.response.EventListResponce;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class QRScanActivity extends BaseActivityClass {

    @BindView(R.id.fram_cam)
    FrameLayout frameLayout;

    @BindView(R.id.codeScannerView)
    CodeScannerView scannerView;
    private CodeScanner mCodeScanner;

    EventListResponce.EventList eventList;

    @Override
    protected int getContentView() {
        return R.layout.activity_qrscan;//your layout
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


                if (separated[0].equalsIgnoreCase("Fin")) {

                    if (separated[1] != null) {

                        frameLayout.setVisibility(View.GONE);

                        callApi(separated[1]);
                    }
                } else {
                    showAlertDialogFinish("Invalid QR Code!");
                }
            } else {
                showAlertDialogFinish("Invalid QR Code!");
            }



        }));
    }

    private void callApi(String s) {

        getCallSociety().scannerData("passAllow",
                s,preferenceManager.getSocietyId(),preferenceManager.getUserId(),"1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        showAlertDialog("No Internet Connection!",R.drawable.ic_error);


                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {

                        if (commonResponse!=null){

                            if (commonResponse.getStatus().equalsIgnoreCase("200")){

                                BasicFunctions.toast(QRScanActivity.this,commonResponse.getMessage(),1);

                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(QRScanActivity.this);
                                dialogBuilder.setTitle(null);
                                dialogBuilder.setIcon(R.drawable.ic_done);
                                dialogBuilder.setMessage(commonResponse.getMessage());
                                dialogBuilder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {dialog.cancel();

                                    Intent returnIntent = new Intent();
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();

                                });

                                dialogBuilder.setCancelable(false);
                                dialogBuilder.show();

                            }else {
                                showAlertDialogFinish(commonResponse.getMessage());

                            }

                        }else {

                            showAlertDialogFinish("Something went wrong! Try again.");
                        }

                    }
                });

    }

    protected void showAlertDialogFinish(String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(null);
        dialogBuilder.setIcon(R.mipmap.ic_launcher);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton(getString(R.string.ok), (dialog, which) ->
        {dialog.cancel();
        finish();});

        dialogBuilder.setCancelable(false);
        dialogBuilder.show();
    }
}
