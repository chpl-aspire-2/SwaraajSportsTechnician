package com.swaraajsports.technician.selectsociety;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.activity.BaseActivityClass;
import com.swaraajsports.technician.activity.LoginActivity;
import com.swaraajsports.technician.dashboard.DashBoardActivity;
import com.swaraajsports.technician.response.SocietyResponse;
import com.swaraajsports.technician.selectsociety.adpater.SocietyAdapter;
import com.swaraajsports.technician.util.Delegate;
import com.swaraajsports.technician.util.VariableBag;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class SelectSocietyActivity extends BaseActivityClass implements SearchView.OnQueryTextListener {

    @BindView(R.id.selectSocietyActivityRecy_society_list)
    RecyclerView selectSocietyActivityRecy_society_list;

    @BindView(R.id.selectSocietyActivityBtn_continue)
    Button selectSocietyActivityBtn_continue;

    @BindView(R.id.selectSocietyActivitysv_society)
    SearchView selectSocietyActivitysv_society;

    @BindView(R.id.selectSocietyActivityRel_nodata)
    RelativeLayout selectSocietyActivityRel_nodata;

    @BindView(R.id.selectSocietyActivityTv_no_data)
    TextView selectSocietyActivityTv_no_data;

    List<SocietyResponse.Society> societyResponce1;
    SocietyAdapter adepter;

    @BindView(R.id.selectSocietyActivityPs_bar)
    ProgressBar selectSocietyActivityPs_bar;

    String societyId, sName, societyAddress;
    String countryId = "0", stateId = "0", cityId = "0";

    boolean isSociety = true;
    boolean isFirebase = true;
    boolean isRequestApply = true;

    boolean isVPNVisible = false;

    private static final int MY_DATA_CHECK_CODE = 121;


    @BindView(R.id.rel_root)
    RelativeLayout rel_root;

    @BindView(R.id.selectSocietyActivityadd_bar)
    AppBarLayout selectSocietyActivityadd_bar;

    @Override
    protected int getContentView() {
        return R.layout.activity_select_society;//your layout
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        setSystemBarColor(SelectSocietyActivity.this);

        Delegate.selectSocietyActivity = this;

        selectSocietyActivitysv_society.setInputType(InputType.TYPE_NULL);

        TextView et = selectSocietyActivitysv_society.findViewById(R.id.search_src_text);
        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            countryId = bundle.getString("countryId");
            stateId = bundle.getString("stateId");
            cityId = bundle.getString("cityId");
            societyResponce1 = new ArrayList<>();

            preferenceManager.setCountryID(countryId);
            preferenceManager.setStateID(stateId);
            preferenceManager.setCityID(cityId);

            initCode();


        }
        setData();

        selectSocietyActivityBtn_continue.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                BasicFunctions.hideSoftKeyboard(SelectSocietyActivity.this, selectSocietyActivitysv_society);

                preferenceManager.setKeyValueString("stateId", stateId);
                preferenceManager.setKeyValueString("cityId", cityId);
                preferenceManager.setKeyValueString("countryId", countryId);
                Intent intent = new Intent(SelectSocietyActivity.this, LoginActivity.class);
                intent.putExtra("societyId", societyId);
                intent.putExtra("countryId", countryId);
                intent.putExtra("stateId", stateId);
                intent.putExtra("cityId", cityId);
                intent.putExtra("sName", sName);
                intent.putExtra("isSociety", isSociety);
                intent.putExtra("isFirebase", isFirebase);
                intent.putExtra("societyAddress", societyAddress);
                startActivity(intent);


            }
        });

    }

    public static void setSystemBarColor(Activity act) {
        Window window = act.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.clearFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS);
        }
        window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
    }


    @SuppressLint("SetTextI18n")
    private void setData() {

        selectSocietyActivityBtn_continue.setText(R.string.Continue);
        selectSocietyActivityTv_no_data.setText(R.string.search_association);
        selectSocietyActivitysv_society.setQueryHint(getString(R.string.search_association));

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (preferenceManager.getLoginSession()) {

            startActivity(new Intent(SelectSocietyActivity.this, DashBoardActivity.class));

        }

    }

    private void initCode() {
        isVPNVisible = false;
        selectSocietyActivitysv_society.setActivated(false);

        selectSocietyActivityPs_bar.setVisibility(View.VISIBLE);
        selectSocietyActivityRel_nodata.setVisibility(View.GONE);
        selectSocietyActivityRecy_society_list.setVisibility(View.GONE);
        selectSocietyActivityBtn_continue.setVisibility(View.GONE);

        RequestBody tag = RequestBody.create("getSociety",MediaType.parse("multipart/form-data"));
        RequestBody ci = RequestBody.create(countryId,MediaType.parse("multipart/form-data"));

        RequestBody soI;
        if (VariableBag.WHITE_LABEL_APP){
            soI = RequestBody.create(VariableBag.WHITE_DEFAULT_SOCIETY_ID,MediaType.parse("multipart/form-data"));

        }else {
            soI = RequestBody.create("",MediaType.parse("multipart/form-data"));

        }


        RequestBody si = RequestBody.create( stateId,MediaType.parse("multipart/form-data"));
        RequestBody cti = RequestBody.create(cityId,MediaType.parse("multipart/form-data"));
        RequestBody langId = RequestBody.create("1",MediaType.parse("multipart/form-data"));

        if (BasicFunctions.vpn(SelectSocietyActivity.this)) {

            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.vpn_connection))
                    .setPositiveButton(getString(R.string.retry), (dialog, which) -> reload())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {

            callMainUrl.getSocietyList(tag,soI, ci, si, cti, langId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<SocietyResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onError(final Throwable e) {

                            runOnUiThread(() -> {
                                selectSocietyActivitysv_society.setActivated(true);
                                selectSocietyActivitysv_society.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                                selectSocietyActivityRecy_society_list.setVisibility(View.GONE);
                                selectSocietyActivityRel_nodata.setVisibility(View.VISIBLE);
                                selectSocietyActivityTv_no_data.setText(getString(R.string.search_association));
                                BasicFunctions.toast(SelectSocietyActivity.this, getString(R.string.no_internet_connection),1);
                            });
                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onNext(final SocietyResponse societyResponse) {

                            runOnUiThread(() -> {

                                new Gson().toJson(societyResponse);

                                selectSocietyActivitysv_society.setActivated(true);
                                selectSocietyActivitysv_society.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

                                isRequestApply = societyResponse.isTake_request_society();



                                if (societyResponse.getStatus() != null && societyResponse.getStatus().equalsIgnoreCase("200")) {

                                    societyResponce1.clear();
                                    societyResponce1.addAll(societyResponse.getSociety());


                                    if (VariableBag.WHITE_LABEL_APP) {
                                        if (societyResponse.getSociety() != null && societyResponse.getSociety().size() == 1) {


                                            societyId = societyResponse.getSociety().get(0).getSocietyId();
                                            sName = societyResponse.getSociety().get(0).getSocietyName();
                                            // isSociety = society.isIs_society();
                                            isSociety = false;
                                            isFirebase = societyResponse.getSociety().get(0).isFirebase();
                                            societyAddress = societyResponse.getSociety().get(0).getSocietyAddress();
                                            preferenceManager.setSocietyId(societyId);
                                            preferenceManager.setBaseUrl(societyResponse.getSociety().get(0).getSubDomain());
                                            preferenceManager.setApikey(societyResponse.getSociety().get(0).getApiKey());
                                            preferenceManager.setSocietyName(societyResponse.getSociety().get(0).getSocietyName());
                                            selectSocietyActivityBtn_continue.setEnabled(true);
                                            selectSocietyActivityBtn_continue.setVisibility(View.VISIBLE);
                                            setButtonBackTint(SelectSocietyActivity.this, selectSocietyActivityBtn_continue, R.color.colorPrimaryDark);
                                            selectSocietyActivityBtn_continue.performClick();

                                        }else {
                                            selectSocietyActivityRecy_society_list.setVisibility(View.VISIBLE);
                                            selectSocietyActivityRel_nodata.setVisibility(View.GONE);
                                            selectSocietyActivityPs_bar.setVisibility(View.GONE);

                                        }
                                    }else {
                                        selectSocietyActivityRecy_society_list.setVisibility(View.GONE);
                                        selectSocietyActivityRel_nodata.setVisibility(View.VISIBLE);
                                        selectSocietyActivityPs_bar.setVisibility(View.GONE);

                                    }


                                    selectSocietyActivityTv_no_data.setText(getString(R.string.search_association));

                                    adepter = new SocietyAdapter(SelectSocietyActivity.this, societyResponce1);

                                    selectSocietyActivityRecy_society_list.setHasFixedSize(true);
                                    selectSocietyActivityRecy_society_list.setLayoutManager(new LinearLayoutManager(SelectSocietyActivity.this));
                                    selectSocietyActivityRecy_society_list.setAdapter(adepter);

                                    adepter.setUpInterface((data, strName, pos, baseUrl, apikey, society, isClick) -> {

                                        BasicFunctions.hideSoftKeyboard(SelectSocietyActivity.this, selectSocietyActivitysv_society);

                                        if (isClick) {
                                            societyId = data;
                                            sName = strName;
                                            // isSociety = society.isIs_society();
                                            isSociety = false;
                                            isFirebase = society.isFirebase();
                                            societyAddress = society.getSocietyAddress();
                                            preferenceManager.setSocietyId(societyId);
                                            preferenceManager.setBaseUrl(baseUrl);
                                            preferenceManager.setApikey(apikey);
                                            preferenceManager.setSocietyName(society.getSocietyName());
                                            selectSocietyActivityBtn_continue.setEnabled(true);
                                            selectSocietyActivityBtn_continue.setVisibility(View.VISIBLE);
                                            setButtonBackTint(SelectSocietyActivity.this, selectSocietyActivityBtn_continue, R.color.colorPrimaryDark);

                                        } else {
                                            setButtonBackTint(SelectSocietyActivity.this, selectSocietyActivityBtn_continue,R.color.grey_500);
                                            selectSocietyActivityBtn_continue.setEnabled(false);

                                        }
                                        adepter.update();
                                    });


                                }
                                else {

                                    selectSocietyActivityRecy_society_list.setVisibility(View.GONE);
                                    selectSocietyActivityRel_nodata.setVisibility(View.VISIBLE);
                                    selectSocietyActivityPs_bar.setVisibility(View.GONE);
                                    selectSocietyActivityTv_no_data.setText(getString(R.string.search_association));

                                }

                            });

                        }
                    });

            selectSocietyActivitysv_society.setOnQueryTextListener(this);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onQueryTextChange(String s) {
        List<SocietyResponse.Society> responceList = new ArrayList<>();
        boolean flag = false;
        if (adepter != null && societyResponce1 != null) {
            if (s.trim().length() > 2) {
                for (int i = 0; i < societyResponce1.size(); i++) {
                    if (societyResponce1.get(i).getSocietyName().trim().toLowerCase().contains(s.trim().toLowerCase())) {
                        responceList.add(societyResponce1.get(i));
                        flag = true;
                    }
                }
                if (flag) {

                    adepter.Update(responceList);
                    selectSocietyActivityRecy_society_list.setVisibility(View.VISIBLE);
                    selectSocietyActivityRel_nodata.setVisibility(View.GONE);
                    selectSocietyActivityBtn_continue.setVisibility(View.VISIBLE);
                    setButtonBackTint(SelectSocietyActivity.this, selectSocietyActivityBtn_continue, R.color.grey_500);
                    selectSocietyActivityBtn_continue.setEnabled(false);


                } else {

                    selectSocietyActivityRecy_society_list.setVisibility(View.GONE);
                    selectSocietyActivityRel_nodata.setVisibility(View.VISIBLE);
                    selectSocietyActivityTv_no_data.setText(getString(R.string.no_association_found));
                    selectSocietyActivityBtn_continue.setVisibility(View.GONE);
                    setButtonBackTint(SelectSocietyActivity.this, selectSocietyActivityBtn_continue, R.color.grey_500);
                    selectSocietyActivityBtn_continue.setEnabled(false);

                }
            } else {
                set();
                selectSocietyActivityBtn_continue.setEnabled(false);
                selectSocietyActivityBtn_continue.setBackground(ContextCompat.getDrawable(SelectSocietyActivity.this, R.drawable.btn_rounded_disable));
                adepter.Update(societyResponce1);
                selectSocietyActivityRecy_society_list.setVisibility(View.GONE);
                selectSocietyActivityRel_nodata.setVisibility(View.VISIBLE);
                selectSocietyActivityTv_no_data.setText(getString(R.string.search_association));
                selectSocietyActivityBtn_continue.setVisibility(View.GONE);

            }
        }
        return false;
    }

    private void set() {
        selectSocietyActivityBtn_continue.setVisibility(View.GONE);
        selectSocietyActivityBtn_continue.setEnabled(false);
        selectSocietyActivityBtn_continue.setTextColor(ContextCompat.getColor(SelectSocietyActivity.this, R.color.grey_10));
        selectSocietyActivityBtn_continue.setBackground(ContextCompat.getDrawable(SelectSocietyActivity.this, R.drawable.btn_rounded_grey_10));

        for (SocietyResponse.Society st : societyResponce1) {
            st.setClicked(false);
        }
    }


    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {

            startActivityForResult(intent, MY_DATA_CHECK_CODE);

        } catch (ActivityNotFoundException a) {
            BasicFunctions.toast(SelectSocietyActivity.this, getString(R.string.speech_not_supported), 1);
        }
    }

    @OnClick(R.id.iv_mice)
    void iv_mice() {
        promptSpeechInput();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_DATA_CHECK_CODE) {

            if (resultCode == RESULT_OK && null != data) {

                ArrayList<String> suggestedWords = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String words = null;

                if (suggestedWords != null) {
                    words = suggestedWords.get(0);
                }


                String[] speakWords = new String[0];
                if (words != null) {
                    speakWords = words.split(" ");
                }

                List<SocietyResponse.Society> responceList = new ArrayList<>();
                boolean flag = false;
                if (adepter != null && societyResponce1 != null) {

                    for (int i = 0; i < societyResponce1.size(); i++) {
                        for (String speak : speakWords) {
                            if (societyResponce1.get(i).getSocietyName().trim().toLowerCase().contains(speak.trim().toLowerCase())) {

                                if (!responceList.contains(societyResponce1.get(i))) {
                                    responceList.add(societyResponce1.get(i));
                                }
                                flag = true;
                            }
                        }
                    }


                    if (flag) {
                        adepter.Update(responceList);
                        selectSocietyActivityRecy_society_list.setVisibility(View.VISIBLE);
                        selectSocietyActivityRel_nodata.setVisibility(View.GONE);
                        selectSocietyActivityBtn_continue.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    public void setButtonBackTint(Context context, Button v, int color){

        v.setBackgroundTintList(ContextCompat.getColorStateList(SelectSocietyActivity.this, color));

    }

}
