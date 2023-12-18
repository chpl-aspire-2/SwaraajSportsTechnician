package com.swaraajsports.technician.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.exoplayer.ExoPlayerView;
import com.swaraajsports.technician.response.ComplainResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class ComplainDetailsActivity extends BaseActivityClass {

    @BindView(R.id.complainDetailsActivityPbar)
    ProgressBar complainDetailsActivityPbar;
    @BindView(R.id.complainDetailsActivityTv_title)
    TextView complainDetailsActivityTv_title;
    @BindView(R.id.complainDetailsActivityTv_cat)
    TextView complainDetailsActivityTv_cat;
    @BindView(R.id.complainDetailsActivityIv_back)
    ImageView complainDetailsActivityIv_back;
    @BindView(R.id.complainDetailsActivityMiddleLine)
    View complainDetailsActivityMiddleLine;

    @BindView(R.id.complainDetailsActivityFabEdit)
    FloatingActionButton complainDetailsActivityFabEdit;

    @BindView(R.id.complainDetailsActivityRvComplainDetails)
    RecyclerView complainDetailsActivityRvComplainDetails;

    boolean isBlocked;
    String complainId, complainNo, complainTitle, complainDesc, categoryId, blockMsg,userId,ComplainStatus;
    ComplainDetailsAdapter complainDetailsAdapter;
    int audioDuration = 0;

    private boolean isNewIntent = true;

    @BindView(R.id.complainDetailsActivityNest)
    NestedScrollView complainDetailsActivityNest;

    ExoPlayerView andExoPlayerView;
    int lastPos = -1;

    private List<ComplaintHelper> complaintHelpers;

    ActivityResultLauncher<Intent> waitResultFor;



    @Override
    protected int getContentView() {
        return R.layout.activity_complain_details;//your layout
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);


        complainId = getIntent().getStringExtra("complainId");
        complainTitle = getIntent().getStringExtra("complainTitle");
        complainNo = getIntent().getStringExtra("complainNo");
        complainDesc = getIntent().getStringExtra("complainDesc");
        blockMsg = getIntent().getStringExtra("blockMsg");
        userId = getIntent().getStringExtra("userId");
        ComplainStatus = getIntent().getStringExtra("ComplainStatus");
        isBlocked = getIntent().getBooleanExtra("isBlocked", false);

        complainDetailsActivityTv_cat.setText(getIntent().getStringExtra("complainCatView") + " ");

        complainDetailsActivityTv_title.setText(complainNo + "");

        complainDetailsActivityRvComplainDetails.setHasFixedSize(true);
        complainDetailsActivityRvComplainDetails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        complainDetailsActivityIv_back.setOnClickListener(v -> finish());
        complainDetailsActivityFabEdit.hide();
        getComplainDetails();

        waitResultFor = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        getComplainDetails();
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isNewIntent = true;
    }

    @SuppressLint("NotifyDataSetChanged")
    @OnClick(R.id.complainDetailsActivityFabEdit)
    void complainDetailsActivityFabEdit() {


        if (isBlocked) {


            BasicFunctions.toast(ComplainDetailsActivity.this,blockMsg,1);


        } else {
            if (isNewIntent) {

                if (andExoPlayerView != null) {
                    andExoPlayerView.pausePlayer();
                }

                if (complainDetailsAdapter != null) {
                    complainDetailsAdapter.notifyDataSetChanged();
                }
                isNewIntent = false;
                Intent intent = new Intent(ComplainDetailsActivity.this, EditComplainActivity.class);
                intent.putExtra("isEdit", true);
                Bundle bundle = new Bundle();
                bundle.putSerializable("complaintHelpers", (Serializable) complaintHelpers);
                intent.putExtras(bundle);
                intent.putExtra("CId", complainId);
                intent.putExtra("CName", complainTitle);
                intent.putExtra("CNo", complainNo);
                intent.putExtra("CDesc", complainDesc);
                intent.putExtra("audioDuration", audioDuration);
                intent.putExtra("CCatId", categoryId);
                intent.putExtra("Ctag", "1");
                intent.putExtra("uId", userId);
                intent.putExtra("ComplainStatus", ComplainStatus);
                waitResultFor.launch(intent);
            }

        }
    }

    public void getComplainDetails() {

        getCallSociety().getComplainDetail("getComplainDetail", preferenceManager.getSocietyId(),
                preferenceManager.getUserId(), complainId, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ComplainResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(() -> {
                            complainDetailsActivityPbar.setVisibility(View.GONE);
                            complainDetailsActivityMiddleLine.setVisibility(View.GONE);
                            BasicFunctions.toast(ComplainDetailsActivity.this, "No Internet Connection", 1);
                        });
                    }

                    @Override
                    public void onNext(final ComplainResponse complainResponse) {

                        runOnUiThread(() -> {
                            complainDetailsActivityPbar.setVisibility(View.GONE);
                            new Gson().toJson(complainResponse);
                            if (complainResponse.getStatus().equalsIgnoreCase("200")) {

                                complainDetailsActivityFabEdit.show();

                                categoryId = complainResponse.getComplainsCategory();
                                complainDetailsActivityRvComplainDetails.setVisibility(View.VISIBLE);
                                complainDetailsActivityNest.setVisibility(View.VISIBLE);
                                complainDetailsActivityMiddleLine.setVisibility(View.VISIBLE);
                                audioDuration = complainResponse.getAudioDuration();
                                List<ComplainResponse.Track> tracks = complainResponse.getTrack();
                                complaintHelpers = complainResponse.getComplainStatusArray();
                                complainDetailsAdapter = new ComplainDetailsAdapter(ComplainDetailsActivity.this, tracks);
                                complainDetailsActivityRvComplainDetails.setAdapter(complainDetailsAdapter);

                                complainDetailsAdapter.setOnClickListener(new ComplainDetailsAdapter.SetOnClickListener() {
                                    @Override
                                    public void onClick(boolean isUpdate) {

                                    }

                                    @Override
                                    public void onImageClick(String url, int pos, ExoPlayerView andExoPlayerView2) {

                                        if (andExoPlayerView2 != null) {
                                            andExoPlayerView2.pausePlayer();
                                        }

                                        openFile(url);


                                    }


                                    @Override
                                    public void isPlaying(ComplainResponse.Track track, int pos, ExoPlayerView andExoPlayerView1) {

                                        if (lastPos != -1 && lastPos != pos) {
                                            if (andExoPlayerView != null) {
                                                andExoPlayerView.pausePlayer();
                                            }
                                        }

                                        lastPos = pos;
                                        andExoPlayerView = andExoPlayerView1;
                                    }
                                });

                                complainDetailsActivityRvComplainDetails.scrollToPosition(tracks.size() - 1);

                                complainDetailsActivityNest.post(() -> complainDetailsActivityNest.scrollTo(0, complainDetailsActivityNest.getBottom()));

                            } else {
                                complainDetailsActivityMiddleLine.setVisibility(View.GONE);
                            }

                        });

                    }
                });
    }

    public void openFile(String url) {

        try {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (url.contains(".doc") || url.contains(".docx")) {
                    // Word document
                    intent.setType("application/msword");
                } else if (url.contains(".pdf")) {
                    // PDF file
                    intent.setType("application/pdf");
                } else if (url.contains(".ppt") || url.contains(".pptx")) {
                    // Powerpoint file
                    intent.setType("application/vnd.ms-powerpoint");
                } else if (url.contains(".xls") || url.contains(".xlsx")) {
                    // Excel file
                    intent.setType("application/vnd.ms-excel");
                } else if (url.contains(".zip") || url.contains(".rar")) {
                    // WAV audio file
                    intent.setType("application/x-wav");
                } else if (url.contains(".rtf")) {
                    // RTF file
                    intent.setType("application/rtf");
                } else if (url.contains(".wav") || url.contains(".mp3")) {
                    // WAV audio file
                    intent.setType("audio/x-wav");
                } else if (url.contains(".gif")) {
                    // GIF file
                    intent.setType("image/gif");
                } else if (url.contains(".txt")) {
                    // Text file
                    intent.setType("text/plain");
                } else if (url.contains(".3gp") || url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")) {
                    // Video files
                    intent.setType("video/*");
                } else {
                    intent.setType("*/*");
                }
                intent.setData(Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            BasicFunctions.toast(ComplainDetailsActivity.this, "No document viewer found", 1);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
