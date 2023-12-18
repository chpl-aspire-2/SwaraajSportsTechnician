package com.swaraajsports.technician.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.ajit.CountryCodePicker;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.dashboard.DashBoardActivity;
import com.swaraajsports.technician.response.CommonResponse;
import com.swaraajsports.technician.response.LoginResponse;
import com.swaraajsports.technician.util.Delegate;
import com.swaraajsports.technician.util.OtpReceivedInterface;
import com.swaraajsports.technician.util.SmsBroadcastReceiver;
import com.swaraajsports.technician.util.VariableBag;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.mukesh.OtpView;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class LoginActivity extends BaseActivityClass implements OtpReceivedInterface {

    @BindView(R.id.parent)
    LinearLayout parent;
    @BindView(R.id.loginActivityLlTop)
    RelativeLayout loginActivityLlTop;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.loginActivityTv_society_name)
    TextView loginActivityTvSocietyName;
    @BindView(R.id.lin_setUp)
    LinearLayout linSetUp;
    @BindView(R.id.tv_initial_setup)
    TextView tvInitialSetup;
    @BindView(R.id.ps_int_load)
    ProgressBar psIntLoad;
    @BindView(R.id.lin_login)
    LinearLayout linLogin;
    @BindView(R.id.loginActivityLlSelectSociety)
    LinearLayout loginActivityLlSelectSociety;
    @BindView(R.id.iv_back_icon)
    ImageView ivBackIcon;
    @BindView(R.id.loginActivityTvSelectOtherBuilding)
    TextView loginActivityTvSelectOtherBuilding;
    @BindView(R.id.loginActivityCcp)
    CountryCodePicker loginActivityCcp;
    @BindView(R.id.loginActivityEtLoginMobile_Email)
    AppCompatEditText loginActivityEtLoginMobileEmail;
    @BindView(R.id.loginActivityBtnLogin)
    Button loginActivityBtnLogin;
    @BindView(R.id.lin_otp)
    LinearLayout lin_otp;


    @BindView(R.id.OTPDialogFragTvCountryCode)
    TextInputEditText OTPDialogFragTvCountryCode;
    @BindView(R.id.OTPDialogFragEt_mobile_register)
    TextInputEditText OTPDialogFragEtMobileRegister;
    @BindView(R.id.OTPDialogFragIv_truemobile_register)
    ImageView OTPDialogFragIvTruemobileRegister;
    @BindView(R.id.OTPDialogFragTVRelexWeWillAuto)
    TextView OTPDialogFragTVRelexWeWillAuto;
    @BindView(R.id.OTPDialogFragTvPleseEnterOTPBelow)
    TextView OTPDialogFragTvPleseEnterOTPBelow;
    @BindView(R.id.OTPDialogFragOtp_view)
    OtpView OTPDialogFragOtpView;
    @BindView(R.id.OTPDialogFragTv_coundown_otp)
    TextView OTPDialogFragTvCoundownOtp;


    boolean isFirebase = false;


    SmsBroadcastReceiver mSmsBroadcastReceiver;

    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;

    private String mVerificationId;


    String otpStr;
    String m_androidId;


    @BindView(R.id.OTPDialogFragLlResend)
    LinearLayout OTPDialogFragLlResend;
    @BindView(R.id.OTPDialogFragBtn_resend_register)
    AppCompatTextView OTPDialogFragBtnResendRegister;
    @BindView(R.id.lin_email_otp)
    LinearLayout linEmailOtp;
    @BindView(R.id.OTPDialogFragTvOr1)
    AppCompatTextView OTPDialogFragTvOr1;
    @BindView(R.id.OTPDialogFragTvBtn_email_otp)
    AppCompatTextView OTPDialogFragTvBtnEmailOtp;
    @BindView(R.id.lin_call_otp)
    LinearLayout linCallOtp;
    @BindView(R.id.OTPDialogFragTvOr)
    AppCompatTextView OTPDialogFragTvOr;
    @BindView(R.id.OTPDialogFragTvBtn_call_otp)
    AppCompatTextView OTPDialogFragTvBtnCallOtp;


    @Override
    protected int getContentView() {
        return R.layout.activity_login;//your layout
    }

    @SuppressLint({"ResourceAsColor", "HardwareIds"})
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent2) {
        super.onViewReady(savedInstanceState, intent2);

        linLogin.setVisibility(View.VISIBLE);
        linSetUp.setVisibility(View.GONE);
        lin_otp.setVisibility(View.GONE);
        OTPDialogFragOtpView.setItemCount(6);
        OTPDialogFragOtpView.setLineColor(R.color.colorPrimary);

        mAuth = FirebaseAuth.getInstance();

        m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        loginActivityBtnLogin.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (Objects.requireNonNull(loginActivityEtLoginMobileEmail.getText()).toString().trim().length() > 7) {

                    linSetUp.setVisibility(View.VISIBLE);
                    linLogin.setVisibility(View.GONE);
                    lin_otp.setVisibility(View.GONE);

                    callApiCheckMobileNumber(loginActivityEtLoginMobileEmail.getText().toString(),"0");

                } else {
                    showAlertDialog("Enter valid mobile number");
                }
            }
        });

        ivBackIcon.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });


        mSmsBroadcastReceiver = new SmsBroadcastReceiver();

        mSmsBroadcastReceiver.setOnOtpListeners(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.EXTRA_SMS_MESSAGE);
        intentFilter.addAction(SmsRetriever.EXTRA_STATUS);
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(mSmsBroadcastReceiver, intentFilter);

        if (VariableBag.WHITE_LABEL_APP){
            loginActivityLlSelectSociety.setVisibility(View.GONE);
        }else {
            loginActivityLlSelectSociety.setVisibility(View.VISIBLE);

        }
    }

    private void callApiCheckMobileNumber(String toString,String otpType) {

        if (VariableBag.WHITE_LABEL_APP){
            if(toString.equalsIgnoreCase("9737564998") || toString.equalsIgnoreCase("8401565883")){
                preferenceManager.setKeyValueString("countryId", "101");
                preferenceManager.setSocietyId("1");
                preferenceManager.setBaseUrl("https:/smart.mysmartsociety.app/demo/");
                preferenceManager.setApikey("smartapikey");
                preferenceManager.setSocietyName("Smart Soceity");

            }
        }


        getCallSociety().getOtp("technicianLogin", preferenceManager.getSocietyId(), loginActivityCcp.getSelectedCountryCodeWithPlus(), toString, isFirebase,
                        otpType,"1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        showAlertDialog(e.getLocalizedMessage());

                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {

                        if (commonResponse != null) {
                            new Gson().toJson(commonResponse);

                            if (commonResponse.getStatus() != null && commonResponse.getStatus().equalsIgnoreCase("200")) {


                                isFirebase = commonResponse.isFirebase();

                                linLogin.setVisibility(View.GONE);
                                linSetUp.setVisibility(View.GONE);
                                lin_otp.setVisibility(View.VISIBLE);

                                startSMSListener();

                                OTPDialogFragTvCountryCode.setText(loginActivityCcp.getSelectedCountryCodeWithPlus());
                                OTPDialogFragEtMobileRegister.setText(toString);
                                OTPDialogFragEtMobileRegister.setEnabled(false);

                                if (isFirebase) {
                                    setUpFireBaseSMS();
                                }

                                setUpOtpListener(commonResponse);

                                getToken();

                            } else {

                                linLogin.setVisibility(View.VISIBLE);
                                linSetUp.setVisibility(View.GONE);
                                lin_otp.setVisibility(View.GONE);

                                showAlertDialog(commonResponse.getMessage());
                            }

                        }

                    }
                });

    }


    private void setUpFireBaseSMS() {

        if (mResendToken != null) {
            resendVerificationCode(loginActivityCcp.getSelectedCountryCodeWithPlus(), Objects.requireNonNull(loginActivityEtLoginMobileEmail.getText()).toString());
        } else {
            sendVerificationCode(loginActivityCcp.getSelectedCountryCodeWithPlus(), Objects.requireNonNull(loginActivityEtLoginMobileEmail.getText()).toString());
        }

    }

    private void resendVerificationCode(String cCode, String mobile) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(cCode + mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(LoginActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                String code = phoneAuthCredential.getSmsCode();

                                if (code != null) {

                                    OTPDialogFragOtpView.setText(code);

                                }
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                mVerificationId = s;
                                mResendToken = forceResendingToken;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(mResendToken)     // ForceResendingToken from callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void sendVerificationCode(String cCode, String mobile) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(cCode + mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(LoginActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                String code = phoneAuthCredential.getSmsCode();

                                if (code != null) {

                                    OTPDialogFragOtpView.setText(code);

                                }
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                mVerificationId = s;
                                mResendToken = forceResendingToken;
                            }
                        })// OnVerificationStateChangedCallbacks
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void verifyVerificationCode(String otp) {

        if (isFirebase) {
            //creating the credential
            try {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
                signInWithPhoneAuthCredential(credential, otp);
            } catch (Exception e) {
                e.printStackTrace();


                //Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                linSetUp.setVisibility(View.GONE);
                linLogin.setVisibility(View.GONE);
                lin_otp.setVisibility(View.VISIBLE);

                callVerify(true, otp);


            }
        }else {
            callVerify(false,otpStr);

        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, String otp) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, task -> {

                    if (task.isSuccessful()) {
                        //verification successful we will start the profile activity

                        callVerify(true,"");

                    } else {

                        linSetUp.setVisibility(View.GONE);
                        linLogin.setVisibility(View.GONE);
                        lin_otp.setVisibility(View.VISIBLE);

                        callVerify(true,otpStr);

                        //verification unsuccessful.. display an error message

                    }
                }).addOnFailureListener(LoginActivity.this, e -> {
                    //Toast.makeText(LoginActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                });
    }


    private void getToken() {

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            if (!TextUtils.isEmpty(token)) {
                preferenceManager.setKeyValueString("token", token);

            } else {

                if (FirebaseInstallations.getInstance().getToken(true).isSuccessful()) {

                    preferenceManager.setKeyValueString("token", FirebaseInstallations.getInstance().getToken(true).getResult().getToken());

                }

            }
        }).addOnFailureListener(e -> {

            if (FirebaseInstallations.getInstance().getToken(true).isSuccessful()) {
                preferenceManager.setKeyValueString("token", FirebaseInstallations.getInstance().getToken(true).getResult().getToken());
            }

            //handle e
        }).addOnCanceledListener(() -> {
            if (FirebaseInstallations.getInstance().getToken(true).isSuccessful()) {
                preferenceManager.setKeyValueString("token", FirebaseInstallations.getInstance().getToken(true).getResult().getToken());
            }
            //handle cancel
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                preferenceManager.setKeyValueString("token", task.getResult());
                Log.v("TAG", "This is the token : " + task.getResult());
            } else {

                if (FirebaseInstallations.getInstance().getToken(true).isSuccessful()) {
                    preferenceManager.setKeyValueString("token", FirebaseInstallations.getInstance().getToken(true).getResult().getToken());
                }
            }
        });

    }

    private void setUpOtpListener(CommonResponse commonResponse) {

        OTPDialogFragOtpView.setOtpCompletionListener(otp -> {
            otpStr = otp;


            linSetUp.setVisibility(View.VISIBLE);
            linLogin.setVisibility(View.GONE);
            lin_otp.setVisibility(View.GONE);

            hideSoftKeyboard();

            if (isFirebase) {
                verifyVerificationCode(otpStr);
            } else {
                callVerify(false,otpStr);
            }

        });

        countDownTimer(commonResponse);
        OTPDialogFragBtnResendRegister.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (isFirebase) {
                    OTPDialogFragLlResend.setVisibility(View.GONE);
                    setUpFireBaseSMS();
                } else {
                    OTPDialogFragLlResend.setVisibility(View.GONE);
                    callApiCheckMobileNumber(Objects.requireNonNull(loginActivityEtLoginMobileEmail.getText()).toString(),"0");
                }

            }
        });

        OTPDialogFragTvBtnCallOtp.setOnClickListener(view -> {

            OTPDialogFragLlResend.setVisibility(View.GONE);
            callApiCheckMobileNumber(Objects.requireNonNull(loginActivityEtLoginMobileEmail.getText()).toString(),"1");


        });

        OTPDialogFragTvBtnEmailOtp.setOnClickListener(view -> {


            OTPDialogFragLlResend.setVisibility(View.GONE);
            callApiCheckMobileNumber(Objects.requireNonNull(loginActivityEtLoginMobileEmail.getText()).toString(),"2");


        });

    }

    private void countDownTimer(CommonResponse commonResponse) {
        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                String text = String.format(Locale.getDefault(), "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(l) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(l) % 60);
                OTPDialogFragTvCoundownOtp.setText(text);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {

                OTPDialogFragIvTruemobileRegister.setVisibility(View.VISIBLE);
                OTPDialogFragLlResend.setVisibility(View.VISIBLE);

                OTPDialogFragTvCoundownOtp.setText("00:00");


                if (commonResponse.isIs_email_otp()){
                    linEmailOtp.setVisibility(View.VISIBLE);
                }else {
                    linEmailOtp.setVisibility(View.GONE);
                }

                if (commonResponse.isVoiceOtp()){
                    linCallOtp.setVisibility(View.VISIBLE);
                }else {
                    linCallOtp.setVisibility(View.GONE);
                }


            }
        };
        countDownTimer.start();
    }

    private void callVerify(Boolean fromFire,String otpStrS) {

        getCallSociety().verifyOtp("technicianVerify",
                        preferenceManager.getSocietyId(),
                        Objects.requireNonNull(loginActivityEtLoginMobileEmail.getText()).toString(),
                        loginActivityCcp.getSelectedCountryCodeWithPlus(),
                        otpStrS,
                        "1",
                        preferenceManager.getKeyValueString("token"),
                        m_androidId,
                        isFirebase,
                        Objects.requireNonNull(loginActivityEtLoginMobileEmail.getText()).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        linSetUp.setVisibility(View.GONE);
                        linLogin.setVisibility(View.GONE);
                        lin_otp.setVisibility(View.VISIBLE);


                    }

                    @Override
                    public void onNext(LoginResponse commonResponse) {

                        if (commonResponse != null) {
                            new Gson().toJson(commonResponse);

                            if (commonResponse.getStatus() != null && commonResponse.getStatus().equalsIgnoreCase("200")) {

                                if (Delegate.selectSocietyActivity != null) {
                                    Delegate.selectSocietyActivity.finish();
                                }

                                preferenceManager.setLoginSession();
                                preferenceManager.setUserId(commonResponse.getUserId());
                                preferenceManager.setUserFullName(commonResponse.getUserName());
                                preferenceManager.setKeyValueString(VariableBag.USER_MOBILE, commonResponse.getUserMobile());
                                preferenceManager.setKeyValueString(VariableBag.COUNTRY_CODE, commonResponse.getCountryCode());

                                startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
                                finish();
                            } else {
                                linSetUp.setVisibility(View.GONE);
                                linLogin.setVisibility(View.GONE);
                                lin_otp.setVisibility(View.VISIBLE);


                                if (fromFire){
                                    String message = "Invalid code entered...";

                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                                    snackbar.setAction("Dismiss", v -> {

                                    });
                                    snackbar.show();

                                }else {
                                    showAlertDialog(commonResponse.getMessage());
                                }

                            }
                        }

                    }
                });


    }

    @Override
    public void onOtpReceived(String otp) {

        OTPDialogFragOtpView.setText(otp);
        otpStr = Objects.requireNonNull(OTPDialogFragOtpView.getText()).toString();

    }

    @Override
    public void onOtpTimeout() {

    }

    @Override
    protected void onDestroy() {
        try {
            if (mSmsBroadcastReceiver != null) {
                unregisterReceiver(mSmsBroadcastReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(aVoid -> {

        });
        mTask.addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Error to detect auto fill OTP", Toast.LENGTH_LONG).show());
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}