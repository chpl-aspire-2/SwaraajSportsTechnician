package com.swaraajsports.technician.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.ajitmaurya.basicsetup.askPermission.PermissionHandler;
import com.ajitmaurya.basicsetup.askPermission.Permissions;
import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.response.ComplainResponse;
import com.swaraajsports.technician.util.MyBounceInterpolator;
import com.swaraajsports.technician.util.PreferenceManager;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class EditComplainActivity extends BaseActivityClass {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_desc)
    TextView tv_desc;

    @BindView(R.id.ps_bar)
    ProgressBar ps_bar;
    @BindView(R.id.relativelayourBtn)
    RelativeLayout relativelayourBtn;
    @BindView(R.id.linaddComplain)
    LinearLayout linaddComplain;

    @BindView(R.id.BtnComlainSave)
    Button saveComplan;
    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.imgBtRecord)
    ImageView imgBtRecord;
    @BindView(R.id.imgBtPause)
    ImageView imgBtPause;
    @BindView(R.id.imgFileCancel)
    ImageView imgFileCancel;
    @BindView(R.id.tvFileName)
    TextView tvFileName;
    @BindView(R.id.tvRecord)
    TextView tvRecord;
    @BindView(R.id.linFileData)
    LinearLayout linFileData;
    @BindView(R.id.chronometer)
    Chronometer chronometer;

    @BindView(R.id.et_reply_msg)
    EditText et_reply_msg;

    PreferenceManager preferenceManager;
    List<String> complainCatId;
    List<String> complainCatName;
    int audioDuration = 0;
    File file;
    File fileUp;
    MultipartBody.Part fileToUpload = null;
    MultipartBody.Part fileToUploadPhoto = null;

    String ComplainId,ComplainStatus, ComplainName, ComplainDec, ComplainPhoto, CNo, complainCatIdStr,uId;
    MediaRecorder mRecorder;
    String fileName;
    int lastProgress = 0;

    String fileStr;
    ArrayList<String> filePathsTemp = new ArrayList<>();
    private boolean flgPic = false;

    private boolean isApiCall = true;

    private boolean animStarted = true;

    @BindView(R.id.viewNewNotification)
    View viewNewNotification;

    @BindView(R.id.main_ps)
    ProgressBar main_ps;

    @BindView(R.id.scroll)
    ScrollView scroll;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.tvTitle)
    TextView tvTitle;


    ActivityResultLauncher<Intent> waitResultForPhoto;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_complain;//your layout
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        ps_bar.setVisibility(View.GONE);

        relativelayourBtn.setVisibility(View.VISIBLE);
        linaddComplain.setVisibility(View.VISIBLE);
        complainCatName = new ArrayList<>();
        complainCatId = new ArrayList<>();
        preferenceManager = new PreferenceManager(this);

        chronometer.setBase(SystemClock.elapsedRealtime());

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            ComplainId = bundle.getString("CId");
            ComplainName = bundle.getString("CName");
            ComplainDec = bundle.getString("CDesc");
            ComplainPhoto = bundle.getString("CPhoto");
            audioDuration = bundle.getInt("audioDuration");
            complainCatIdStr = bundle.getString("CCatId");
            ComplainStatus = bundle.getString("ComplainStatus");
            uId = bundle.getString("uId","0");


            tv_title.setText(ComplainName);
            tv_desc.setText(ComplainDec);

            CNo = bundle.getString("CNo");
            tvTitle.setText(CNo + " edit complain"  );

            et_reply_msg.setHint("Complaint Reply Message *");
            tvRecord.setText("Record Audio");
            saveComplan.setText("Save");
        }

        saveComplan.setOnClickListener(v -> {

            if (et_reply_msg.getText().toString().trim().length() > 0) {

                onValidationSucceeded();

            } else {
                et_reply_msg.requestFocus();
                et_reply_msg.setError("Enter valid reply message");
            }

        });


        waitResultForPhoto= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        String onPhotoTaken;
                        if (result.getData() != null) {
                            onPhotoTaken = result.getData().getStringExtra("onPhotoTaken");
                            filePathsTemp.add(onPhotoTaken);
                            flgPic = true;
                            BasicFunctions.displayImageBG(EditComplainActivity.this, iv_profile, filePathsTemp.get(0));

                        }

                    }
                });

        ivBack.setOnClickListener(view -> finish());

    }

    public void onValidationSucceeded() {

        if (isApiCall) {
            isApiCall = false;
            relativelayourBtn.setVisibility(View.GONE);
            linaddComplain.setVisibility(View.GONE);
            ps_bar.setVisibility(View.VISIBLE);

            scroll.setVisibility(View.GONE);
            main_ps.setVisibility(View.VISIBLE);

            prepareforStop();
            stopRecording();


            RequestBody tag = RequestBody.create(MediaType.parse("text/plain"), "replyComplain");
            RequestBody cId = RequestBody.create(MediaType.parse("text/plain"), ComplainId);
            RequestBody societyId = RequestBody.create(MediaType.parse("text/plain"), preferenceManager.getSocietyId());
            RequestBody complainTitle = RequestBody.create(MediaType.parse("text/plain"), ComplainName);
            RequestBody complainDescription = RequestBody.create(MediaType.parse("text/plain"), et_reply_msg.getText().toString());
            RequestBody blockUnitname = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody blockUnitId = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody user_id = RequestBody.create(uId ,MediaType.parse("text/plain"));
            RequestBody compainNo = RequestBody.create( CNo,MediaType.parse("text/plain"));
            RequestBody empId = RequestBody.create( preferenceManager.getUserId(),MediaType.parse("text/plain"));

            RequestBody complainCategoy = RequestBody.create(MediaType.parse("text/plain"), complainCatIdStr);

            RequestBody caomplaintStatus = RequestBody.create(MediaType.parse("text/plain"), ComplainStatus);
            RequestBody langId = RequestBody.create(MediaType.parse("text/plain"), "1");

            if (flgPic && filePathsTemp.size() > 0) {
                File file = new File(BasicFunctions.compressImage(EditComplainActivity.this, filePathsTemp.get(0)));
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                fileToUploadPhoto = MultipartBody.Part.createFormData("complains_track_img", file.getName(), requestBody);
            }

            getCallSociety().editComplain(
                    tag
                    , societyId,
                    cId,
                    empId,
                    complainTitle
                    ,complainDescription,
                    fileToUploadPhoto,
                    blockUnitId,
                    user_id,
                    blockUnitname,
                    complainCategoy,
                    caomplaintStatus,
                    compainNo,
                    fileToUpload,
                    langId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<ComplainResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(final Throwable throwable) {

                            runOnUiThread(() -> {
                                isApiCall = true;
                                BasicFunctions.toast(EditComplainActivity.this, throwable.getMessage(), 1);

                                finish();
                            });
                        }

                        @Override
                        public void onNext(final ComplainResponse complainResponse) {

                            runOnUiThread(() -> {
                                isApiCall = true;
                                new Gson().toJson(complainResponse);

                                if (complainResponse.getStatus().equalsIgnoreCase("200")) {
                                    BasicFunctions.toast(EditComplainActivity.this, complainResponse.getMessage(), 2);

                                    fileStr = null;

                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    scroll.setVisibility(View.VISIBLE);
                                    main_ps.setVisibility(View.GONE);
                                    BasicFunctions.toast(EditComplainActivity.this, complainResponse.getMessage(), 1);
                                }
                            });

                        }
                    });
        }
    }

    @OnClick(R.id.iv_profile)
    public void iv_profile() {
        StrictMode.VmPolicy.Builder builder2 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder2.build());

        final CharSequence[] options = {"Upload Photo","Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Upload Photo")) {

                Permissions.check(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, getString(R.string.camera_storege_per), null, new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        dialog.dismiss();
                        filePathsTemp.clear();
                        flgPic = false;
                        iv_profile.setImageResource(R.drawable.addphotos);

                        Intent i = new Intent(EditComplainActivity.this,ClickImageActivity.class);
                        i.putExtra("picCount",1);
                        i.putExtra("isGallery", false);
                        waitResultForPhoto.launch(i);

                    }
                });

            } else if (options[item].equals("Choose From Gallery")) {

                Permissions.check(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, getString(R.string.camera_storege_per), null, new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        dialog.dismiss();
                        filePathsTemp.clear();
                        flgPic = false;
                        iv_profile.setImageResource(R.drawable.addphotos);

                        Intent i = new Intent(EditComplainActivity.this, ClickImageActivity.class);
                        i.putExtra("picCount",1);
                        i.putExtra("isGallery", true);
                        waitResultForPhoto.launch(i);

                    }
                });

            }else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    @OnClick(R.id.imgBtRecord)
    void imgBtRecord() {

        Permissions.check(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, getString(R.string.storege_per)+" "+getString(R.string.micro_per), null, new PermissionHandler() {
            @Override
            public void onGranted() {
                if (BasicFunctions.getMicrophoneAvailable(EditComplainActivity.this)) {
                    prepareRecording();
                    startRecording();
                } else {

                    BasicFunctions.toast(EditComplainActivity.this,"Mic is unavailable",1);

                }
            }
        });

    }

    @OnClick(R.id.imgBtPause)
    void imgBtPause() {
        prepareforStop();
        stopRecording();
        stopAnim();
    }

    private void prepareRecording() {
        //TransitionManager.beginDelayedTransition(linearLayoutRecorder);
        imgBtRecord.setVisibility(View.GONE);
        imgBtPause.setVisibility(View.VISIBLE);
        iv_profile.setEnabled(false);
        iv_profile.setClickable(false);
        startAnim();
        //linearLayoutPlay.setVisibility(View.GONE);
    }

    private void startRecording() {
        linFileData.setVisibility(View.GONE);
        //we use the MediaRecorder class to record
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setMaxDuration(audioDuration);
        mRecorder.setOnInfoListener((mediaRecorder, i, i1) -> {
            if (i == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {

                stopAnim();
                prepareforStop();
                stopRecording();
            }
        });


        file = new File(BasicFunctions.getDefaultPath(EditComplainActivity.this));
        if (!file.exists()) {
            file.mkdirs();
        }

        fileName = file.getAbsolutePath() + System.currentTimeMillis() + preferenceManager.getUserId() + ".mp3";
        Log.d("filename", fileName);
        mRecorder.setOutputFile(fileName);

        fileUp = new File(fileName);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), fileUp);
        fileToUpload = MultipartBody.Part.createFormData("complains_track_voice", fileUp.getName(), requestBody);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastProgress = 0;

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void prepareforStop() {
        imgBtRecord.setVisibility(View.VISIBLE);
        imgBtPause.setVisibility(View.GONE);
        iv_profile.setEnabled(true);
        iv_profile.setClickable(true);

    }

    private void stopRecording() {

        try {
            linFileData.setVisibility(View.VISIBLE);
            if (fileUp!=null) {
                tvFileName.setText(fileUp.getName());
            }

            if (mRecorder!=null) {
                mRecorder.stop();
                mRecorder.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mRecorder = null;
        chronometer.stop();
    }

    private void stopAnim() {
        runOnUiThread(() -> {
            animStarted = false;
            if (viewNewNotification != null) {
                if (viewNewNotification.getAnimation() != null) {
                    viewNewNotification.getAnimation().cancel();
                }
                viewNewNotification.clearAnimation();
                viewNewNotification.setVisibility(View.GONE);
            }

        });

    }

    private void startAnim() {
        Animation bounceAnim = AnimationUtils.loadAnimation(EditComplainActivity.this, R.anim.bounce_one);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounceAnim.setInterpolator(interpolator);

        if (viewNewNotification != null) {

            viewNewNotification.setVisibility(View.VISIBLE);
            viewNewNotification.startAnimation(bounceAnim);
        }
        bounceAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animStarted) {
                    if (viewNewNotification != null) {

                        viewNewNotification.startAnimation(bounceAnim);
                    }
                }
            }
        });
    }

    @OnClick(R.id.imgFileCancel)
    void imgFileCancel() {
        linFileData.setVisibility(View.GONE);
        try {

            fileToUpload = null;

            mRecorder.stop();
            mRecorder.release();

            if (fileUp != null) {

                if (fileUp.exists()) {
                    if (fileUp.delete()) {
                        System.out.println("file Deleted :");
                    } else {
                        System.out.println("file not Deleted :");
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecorder = null;
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

}