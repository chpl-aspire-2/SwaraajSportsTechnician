package com.swaraajsports.technician.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.adapter.ComplainAdapter;
import com.swaraajsports.technician.response.CommonResponse;
import com.swaraajsports.technician.response.ComplainResponse;
import com.google.gson.Gson;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class ComplainActivity extends BaseActivityClass {
    String status;

    @BindView(R.id.complainFragRe_Complains)
    RecyclerView complainFragRe_Complains;
    @BindView(R.id.complainFragPullToRefresh)
    SwipeRefreshLayout complainFragPullToRefresh;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.complainFragLinLayNoData)
    LinearLayout complainFragLinLayNoData;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.tv_title)
    TextView tv_title;


    ComplainAdapter complainAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_complain;//your layout
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        tv_title.setText("Complain");


        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            status = bundle.getString("status");

            complainFragRe_Complains.setLayoutManager(new LinearLayoutManager(this));

            initCode();

        }


        ivBack.setOnClickListener(view -> finish());
    }

    private void initCode(){
        complainFragRe_Complains.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        complainFragLinLayNoData.setVisibility(View.GONE);

        getListEvent();

    }

    private void getListEvent() {

        getCallSociety().getEventList("getComplainNew",
                preferenceManager.getSocietyId(),
                preferenceManager.getUserId(), status,"1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ComplainResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        complainFragRe_Complains.setVisibility(View.GONE);
                        progress.setVisibility(View.GONE);
                        complainFragLinLayNoData.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onNext(ComplainResponse complainResponse) {

                        new Gson().toJson(complainResponse);

                        if (complainResponse != null && complainResponse.getStatus().equalsIgnoreCase("200")) {
                            complainFragRe_Complains.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                            complainFragLinLayNoData.setVisibility(View.GONE);


                            if (complainAdapter != null && complainResponse.getComplain() != null && complainResponse.getComplain().size() > 0) {
                                complainAdapter.upDateData(complainResponse);
                            } else {
                                if (complainResponse.getComplain() != null && complainResponse.getComplain().size() > 0) {
                                    complainAdapter = new ComplainAdapter(ComplainActivity.this, complainResponse.getComplain());
                                    complainFragRe_Complains.setAdapter(complainAdapter);
                                }
                            }

                            complainAdapter.setOnClickListener(new ComplainAdapter.SetOnClickListener() {
                                @Override
                                public void onClick(int position, ComplainResponse.Complain complain) {

                                    Intent intent = new Intent(ComplainActivity.this, ComplainDetailsActivity.class);
                                    intent.putExtra("complainId", complain.getComplain_id());
                                    intent.putExtra("complainTitle", complain.getCompalain_title());
                                    intent.putExtra("complainNo", complain.getComplain_no());
                                    intent.putExtra("complainDesc", complain.getComplain_description());
                                    intent.putExtra("complainCatId", complain.getComplaint_category());
                                    intent.putExtra("complainCatView", complain.getComplaint_category_view());
                                    intent.putExtra("ComplainStatus", complain.getComplain_status());
                                    intent.putExtra("isBlocked", false);
                                    intent.putExtra("userId", complain.getUser_id());
                                    intent.putExtra("blockMsg", "");
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


                                    final Dialog dialog = new Dialog(ComplainActivity.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.dialog_reason_hold);

                                    EditText etComments = (EditText) dialog.findViewById(R.id.etReason);

                                    Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
                                    Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

                                    dialogButton.setOnClickListener(v -> {
                                        if (etComments.getText().toString().trim().length() > 0) {
                                            dialog.dismiss();
                                            callClose(complain,true,etComments.getText().toString());
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
                                    callInProgress(complain);
                                }

                                @Override
                                public void setClose(ComplainResponse.Complain complain) {

                                    callClose(complain,false,"");
                                }

                                @Override
                                public void setReqClose(ComplainResponse.Complain complain) {

                                    callRequestClose(complain);

                                }
                            });


                        }else {
                            complainFragRe_Complains.setVisibility(View.GONE);
                            progress.setVisibility(View.GONE);
                            complainFragLinLayNoData.setVisibility(View.VISIBLE);

                        }
                    }
                });


    }

    private void callRequestClose(ComplainResponse.Complain complain) {

        complainFragRe_Complains.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        complainFragLinLayNoData.setVisibility(View.GONE);


        getCallSociety().closeComplainRequest("closeComplainRequest",
                preferenceManager.getSocietyId(),
                preferenceManager.getUserId(),
                preferenceManager.getUserName(),
                complain.getComplain_id(),
                "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        complainFragRe_Complains.setVisibility(View.GONE);
                        progress.setVisibility(View.GONE);
                        complainFragLinLayNoData.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {
                        getListEvent();
                    }
                });
    }

    private void callClose(ComplainResponse.Complain complain,boolean isHold,String holdMsg) {

        complainFragRe_Complains.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        complainFragLinLayNoData.setVisibility(View.GONE);


        getCallSociety().closeComplainAuto("closeComplainAuto",
                preferenceManager.getSocietyId(),
                preferenceManager.getUserId(),
                complain.getComplain_id(),
                        isHold ? "1":"",
                        holdMsg,
                "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        complainFragRe_Complains.setVisibility(View.GONE);
                        progress.setVisibility(View.GONE);
                        complainFragLinLayNoData.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {
                        getListEvent();
                    }
                });
    }

    private void callInProgress(ComplainResponse.Complain complain) {

        complainFragRe_Complains.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        complainFragLinLayNoData.setVisibility(View.GONE);

        getCallSociety().inProgressComplain("inProgressComplain",
                preferenceManager.getSocietyId(),
                preferenceManager.getUserId(),
                complain.getComplain_id(),
                "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        complainFragRe_Complains.setVisibility(View.GONE);
                        progress.setVisibility(View.GONE);
                        complainFragLinLayNoData.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {
                        getListEvent();

                    }
                });

    }

}