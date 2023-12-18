package com.swaraajsports.technician.selectsociety;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.activity.BaseActivityClass;
import com.swaraajsports.technician.response.LocationResponse;
import com.swaraajsports.technician.selectsociety.adpater.SpinAdapter;
import com.swaraajsports.technician.util.ConnectivityListner;
import com.swaraajsports.technician.util.Delegate;
import com.swaraajsports.technician.util.InternetConnection;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class FilterActivity extends BaseActivityClass implements ConnectivityListner.OnCustomStateListener {

    final BroadcastReceiver mybroadcast = new InternetConnection();

    @BindView(R.id.filterActivityTvCountry)
    TextView filterActivityTvCountry;
    @BindView(R.id.filterActivityLinCountry)
    LinearLayout filterActivityLinCountry;

    @BindView(R.id.filterActivityTvState)
    TextView filterActivityTvState;
    @BindView(R.id.filterActivityLinState)
    LinearLayout filterActivityLinState;

    @BindView(R.id.filterActivityTvCity)
    TextView filterActivityTvCity;
    @BindView(R.id.filterActivityLinCity)
    LinearLayout filterActivityLinCity;

    @BindView(R.id.filterActivityPsBar)
    ProgressBar filterActivityPsBar;

    @BindView(R.id.filterActivityCardLocation)
    LinearLayout filterActivityCardLocation;

    @BindView(R.id.filterActivityIvBack)
    ImageView filterActivityIvBack;

    @BindView(R.id.filterActivityCirImageLogo)
    CircleImageView filterActivityCirImageLogo;

    @BindView(R.id.filterActivityBtnNext)
    Button filterActivityBtnNext;

    EditText spsEditText;

    LinearLayout linNoDataFound;
    BottomSheetDialog dialog;
    List<LocationHelper> city = new ArrayList<>();
    List<LocationHelper> country = new ArrayList<>();
    List<LocationHelper> states = new ArrayList<>();
    String countryId = "-1", stateId = "-1", cityId = "-1";

    String TempcountryId = "-1", TempstateId = "-1", TempcityId = "-1";

    String countryName = "", stateName = "", cityName = "", countryCode = "";
    String TempcountryName = "", TempstateName = "", TempcityName = "";
    LocationResponse locationRespo;

    private SpinAdapter adaptercountry;
    private SpinAdapter adapterstate;
    private SpinAdapter adaptercity;
    private ImageView no_area_found_tv;
    private TextView tv_title_no_data;

    boolean isVPNVisible = false;


    @Override
    protected int getContentView() {
        return R.layout.activity_filter;//your layout
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        Paper.book().destroy();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Delegate.filterActivity = this;

        // BasicFunctions.displayImage(this, filterActivityIvBack, "");

        if (dialog == null) {
            dialog = new BottomSheetDialog(FilterActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.my_customdialog_internet);
            dialog.setCancelable(false);
        }

        ConnectivityListner.getInstance().setListener(this);
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mybroadcast, intentFilter);


        setData();
    }


    private void setData() {

        filterActivityTvCountry.setText(getString(R.string.select_country));
        filterActivityTvState.setText(getString(R.string.select_state));
        filterActivityTvCity.setText(getString(R.string.select_city));
        filterActivityBtnNext.setText(getString(R.string.next));


    }

    private void initCode() {

        isVPNVisible = false;
        if (BasicFunctions.vpn(FilterActivity.this)) {
            if (!isVPNVisible) {
                isVPNVisible = true;
                showVPNDialog();
            }
        } else {

            filterActivityPsBar.setVisibility(View.VISIBLE);
            filterActivityCardLocation.setVisibility(View.GONE);
            filterActivityCirImageLogo.setVisibility(View.GONE);

            callMainUrl.getCountry("getCountries", "1")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LocationResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                            if (!FilterActivity.this.isDestroyed()) {

                                BasicFunctions.toast(FilterActivity.this, getString(R.string.no_internet_connection), 1);


                            }

                        }

                        @Override
                        public void onNext(final LocationResponse locationResponse) {

                            if (!FilterActivity.this.isDestroyed()) {
                                new Gson().toJson(locationResponse);

                                if (locationResponse.getStatus().equalsIgnoreCase("200")) {

                                    locationRespo = locationResponse;
                                    filterActivityPsBar.setVisibility(View.GONE);
                                    filterActivityCardLocation.setVisibility(View.VISIBLE);
                                    filterActivityCirImageLogo.setVisibility(View.VISIBLE);

                                    country = new ArrayList<>();

                                    country.clear();

                                    if (locationResponse.getCountries() != null) {
                                        for (int i = 0; i < locationResponse.getCountries().size(); i++) {

                                            country.add(new LocationHelper(locationResponse.getCountries().get(i).getName(),
                                                    locationResponse.getCountries().get(i).getNameSearch(),
                                                    locationResponse.getCountries().get(i).getCountryId(),
                                                    locationResponse.getCountries().get(i).getCountryCode()));


                                        }
                                    }

                                    filterActivityLinCountry.setOnClickListener(new OnSingleClickListener() {
                                        @Override
                                        public void onSingleClick(View v) {
                                            lin_country();
                                        }
                                    });

                                    filterActivityLinState.setOnClickListener(new OnSingleClickListener() {
                                        @Override
                                        public void onSingleClick(View v) {
                                            lin_state();
                                        }
                                    });

                                    filterActivityLinCity.setOnClickListener(new OnSingleClickListener() {
                                        @Override
                                        public void onSingleClick(View v) {
                                            lin_city();
                                        }
                                    });
                                } else {
                                    BasicFunctions.toast(FilterActivity.this, "" + locationResponse.getMessage(), 1);
                                }

                            }
                        }
                    });


        }

    }

    @SuppressLint("SetTextI18n")
    public void lin_country() {
        filterActivityBtnNext.setClickable(true);
        RecyclerView areaListRv;
        Button doneBtn, cancelBt;
        EditText etSearch;

        final Dialog dialogBuilder = new Dialog(FilterActivity.this);
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialogBuilder.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBuilder.setContentView(R.layout.select_area_dialog);
        etSearch = dialogBuilder.findViewById(R.id.etSearch);
        areaListRv = dialogBuilder.findViewById(R.id.area_list_rv);
        linNoDataFound = dialogBuilder.findViewById(R.id.linNodataFound);
        tv_title_no_data = dialogBuilder.findViewById(R.id.tv_title_no_data);
        no_area_found_tv = dialogBuilder.findViewById(R.id.no_area_found_tv);

        no_area_found_tv.setImageResource(R.drawable.ic_no_country_found_icon);
        doneBtn = dialogBuilder.findViewById(R.id.done_btn);
        cancelBt = dialogBuilder.findViewById(R.id.cancel_bt);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.VERTICAL, false);
        areaListRv.setLayoutManager(layoutManager);
        areaListRv.setItemAnimator(new DefaultItemAnimator());
        dialogBuilder.setCancelable(false);

        spsEditText = dialogBuilder.findViewById(R.id.spEditText);

        spsEditText.setHint(getString(R.string.search_country));
        etSearch.setHint(getString(R.string.search_country));
        cancelBt.setText(getString(R.string.cancel));
        doneBtn.setText(getString(R.string.done));
        tv_title_no_data.setText(getString(R.string.no_country_found));


        doneBtn.setOnClickListener(v -> {

            if (TempcountryId.equalsIgnoreCase("-1")) {

                BasicFunctions.toast(FilterActivity.this, getString(R.string.search_country), 1);
            } else {
                dialogBuilder.dismiss();

                countryId = TempcountryId;
                countryName = TempcountryName;


                BasicFunctions.hideSoftKeyboard(FilterActivity.this);

                filterActivityTvCountry.setText("" + countryName);

                ClearState();
                ClearCity();


                if (BasicFunctions.vpn(FilterActivity.this)) {

                    showVPNDialog();


                } else {


                    callMainUrl.getState("getState", countryId, "1")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<LocationResponse>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                    BasicFunctions.toast(FilterActivity.this, getString(R.string.no_internet_connection), 1);


                                }

                                @Override
                                public void onNext(final LocationResponse locationResponse) {
                                    new Gson().toJson(locationResponse);

                                    if (locationResponse.getStatus().equalsIgnoreCase("200")) {

                                        states = new ArrayList<>();
                                        states.clear();

                                        for (int i = 0; i < locationResponse.getStates().size(); i++) {
                                            states.add(new LocationHelper(locationResponse.getStates().get(i).getName(),
                                                    locationResponse.getStates().get(i).getNameSearch(),
                                                    locationResponse.getStates().get(i).getStateId()));
                                        }
                                    } else {
                                        BasicFunctions.toast(FilterActivity.this, "" + locationResponse.getMessage(), 1);
                                    }
                                }
                            });
                }
            }
        });

        cancelBt.setOnClickListener(v -> dialogBuilder.dismiss());

        dialogBuilder.show();

        if (country != null) {
            adaptercountry = new SpinAdapter(FilterActivity.this, country, Integer.parseInt(countryId), countryId);

            adaptercountry.setOnItemClickListener(new SpinAdapter.AreaSingleClickListener() {
                @Override
                public void onItemClickListener(String name, String id, int position, LocationHelper help) {
                    adaptercountry.selectedItem();

                    TempcountryId = id;
                    TempcountryName = name;
                    countryCode = help.cCode;

                    BasicFunctions.hideSoftKeyboard(FilterActivity.this);
                }
            });
            areaListRv.setAdapter(adaptercountry);
        }

        spsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {




            }

            @Override
            public void afterTextChanged(Editable s) {

                String newText = s.toString();

                if (country != null) {
                    List<LocationHelper> countryFilter = new ArrayList<>();

                    if (newText.length() > 0) {
                        for (int i = 0; i < country.size(); i++) {

                            if (country.get(i).nameSearch.toLowerCase().contains(newText.toLowerCase())
                                    || country.get(i).name.toLowerCase().contains(newText.toLowerCase())) {
                                countryFilter.add(country.get(i));
                            }

                        }
                        if (countryFilter.size() > 0)
                            linNoDataFound.setVisibility(View.GONE);
                        else
                            linNoDataFound.setVisibility(View.VISIBLE);
                    } else {
                        countryFilter = country;
                        linNoDataFound.setVisibility(View.GONE);
                    }
                    adaptercountry.updateSearch(countryFilter);
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().trim();
                if (country != null) {
                    List<LocationHelper> countryFilter = new ArrayList<>();

                    if (newText.length() > 0) {
                        for (int i = 0; i < country.size(); i++) {

                            if (country.get(i).nameSearch.toLowerCase().contains(newText.toLowerCase())
                                    || country.get(i).name.toLowerCase().contains(newText.toLowerCase())) {
                                countryFilter.add(country.get(i));
                            }

                        }
                        if (countryFilter.size() > 0)
                            linNoDataFound.setVisibility(View.GONE);
                        else
                            linNoDataFound.setVisibility(View.VISIBLE);
                    } else {
                        countryFilter = country;
                        linNoDataFound.setVisibility(View.GONE);
                    }
                    adaptercountry.updateSearch(countryFilter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void lin_state() {
        filterActivityBtnNext.setClickable(true);
        RecyclerView areaListRv;
        Button doneBtn, cancelBt;
        EditText etSearch;

        final Dialog dialogBuilder = new Dialog(FilterActivity.this);
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialogBuilder.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBuilder.setContentView(R.layout.select_area_dialog);
        etSearch = dialogBuilder.findViewById(R.id.etSearch);
        areaListRv = dialogBuilder.findViewById(R.id.area_list_rv);
        linNoDataFound = dialogBuilder.findViewById(R.id.linNodataFound);
        tv_title_no_data = dialogBuilder.findViewById(R.id.tv_title_no_data);
        no_area_found_tv = dialogBuilder.findViewById(R.id.no_area_found_tv);
        no_area_found_tv.setImageResource(R.drawable.ic_no_city);
        doneBtn = dialogBuilder.findViewById(R.id.done_btn);
        cancelBt = dialogBuilder.findViewById(R.id.cancel_bt);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.VERTICAL, false);
        areaListRv.setLayoutManager(layoutManager);
        areaListRv.setItemAnimator(new DefaultItemAnimator());
        spsEditText = dialogBuilder.findViewById(R.id.spEditText);
        dialogBuilder.setCancelable(false);

//        if (preferenceManager.getJSONKeyStringObject("cancel").trim().length() > 2) {
//            spsEditText.SetHint(preferenceManager.getJSONKeyStringObject("search_state"));
//            etSearch.setHint(preferenceManager.getJSONKeyStringObject("search_state"));
//            cancelBt.setText(preferenceManager.getJSONKeyStringObject("cancel"));
//            doneBtn.setText(preferenceManager.getJSONKeyStringObject("done"));
//            tv_title_no_data.setText(preferenceManager.getJSONKeyStringObject("no_state_found"));
//        } else {
        spsEditText.setHint(getString(R.string.search_state));
        etSearch.setHint(getString(R.string.search_state));
        cancelBt.setText(getString(R.string.cancel));
        doneBtn.setText(getString(R.string.done));
        tv_title_no_data.setText(getString(R.string.no_state_found));

        // }

        doneBtn.setOnClickListener(v -> {

            if (!TempstateId.equals("-1")) {

                dialogBuilder.dismiss();

                stateId = TempstateId;
                stateName = TempstateName;

                filterActivityTvState.setText("" + stateName);

                ClearCity();

                BasicFunctions.hideSoftKeyboard(FilterActivity.this);

                if (BasicFunctions.vpn(FilterActivity.this)) {

                    showVPNDialog();


                } else {

                    callMainUrl.getCity("getCity", stateId, "1")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<LocationResponse>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    BasicFunctions.toast(FilterActivity.this, getString(R.string.no_internet_connection), 1);
                                }

                                @Override
                                public void onNext(final LocationResponse locationResponse) {
                                    new Gson().toJson(locationResponse);

                                    if (locationResponse.getStatus().equalsIgnoreCase("200")) {

                                        city = new ArrayList<>();
                                        city.clear();

                                        for (int i = 0; i < locationResponse.getCities().size(); i++) {

                                            city.add(new LocationHelper(locationResponse.getCities().get(i).getName(),
                                                    locationResponse.getCities().get(i).getNameSearch(),
                                                    locationResponse.getCities().get(i).getCity_id()));

                                        }


                                    } else {
                                        BasicFunctions.toast(FilterActivity.this, "" + locationResponse.getMessage(), 1);
                                    }
                                }
                            });
                }

            } else {

                BasicFunctions.toast(FilterActivity.this, getString(R.string.please_select_state), 1);

            }

        });

        cancelBt.setOnClickListener(v -> dialogBuilder.dismiss());

        dialogBuilder.show();

        if (states != null) {

            adapterstate = new SpinAdapter(FilterActivity.this, states, Integer.parseInt(stateId), stateId);

            adapterstate.setOnItemClickListener(new SpinAdapter.AreaSingleClickListener() {
                @Override
                public void onItemClickListener(String name, String id, int position, LocationHelper help) {
                    adapterstate.selectedItem();

                    TempstateId = id;
                    TempstateName = name;

                    BasicFunctions.hideSoftKeyboard(FilterActivity.this);

                }
            });

            areaListRv.setAdapter(adapterstate);

        }


        spsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString();
                if (states != null) {
                    List<LocationHelper> stateFilter = new ArrayList<>();

                    if (newText.length() > 0) {
                        for (int i = 0; i < states.size(); i++) {

                            if (states.get(i).nameSearch.toLowerCase().contains(newText.toLowerCase())
                                    || states.get(i).name.toLowerCase().contains(newText.toLowerCase())) {
                                stateFilter.add(states.get(i));
                            }

                        }
                        if (stateFilter.size() > 0)
                            linNoDataFound.setVisibility(View.GONE);
                        else
                            linNoDataFound.setVisibility(View.VISIBLE);
                    } else {
                        stateFilter = states;
                        linNoDataFound.setVisibility(View.GONE);
                    }
                    adapterstate.updateSearch(stateFilter);
                }
            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().trim();
                if (states != null) {
                    List<LocationHelper> stateFilter = new ArrayList<>();

                    if (newText.length() > 0) {
                        for (int i = 0; i < states.size(); i++) {

                            if (states.get(i).name.toLowerCase().contains(newText.toLowerCase())
                                    || states.get(i).nameSearch.toLowerCase().contains(newText.toLowerCase())) {
                                stateFilter.add(states.get(i));
                            }

                        }
                        if (stateFilter.size() > 0)
                            linNoDataFound.setVisibility(View.GONE);
                        else
                            linNoDataFound.setVisibility(View.VISIBLE);
                    } else {
                        stateFilter = states;
                        linNoDataFound.setVisibility(View.GONE);
                    }
                    adapterstate.updateSearch(stateFilter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @SuppressLint("SetTextI18n")
    public void lin_city() {

        filterActivityBtnNext.setClickable(true);
        RecyclerView areaListRv;
        Button doneBtn, cancelBt;
        EditText etSearch;

        final Dialog dialogBuilder = new Dialog(FilterActivity.this);
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialogBuilder.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBuilder.setContentView(R.layout.select_area_dialog);
        areaListRv = dialogBuilder.findViewById(R.id.area_list_rv);
        linNoDataFound = dialogBuilder.findViewById(R.id.linNodataFound);
        tv_title_no_data = dialogBuilder.findViewById(R.id.tv_title_no_data);

        no_area_found_tv = dialogBuilder.findViewById(R.id.no_area_found_tv);
        no_area_found_tv.setImageResource(R.drawable.ic_no_city);
        etSearch = dialogBuilder.findViewById(R.id.etSearch);
        doneBtn = dialogBuilder.findViewById(R.id.done_btn);
        cancelBt = dialogBuilder.findViewById(R.id.cancel_bt);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.VERTICAL, false);
        areaListRv.setLayoutManager(layoutManager);
        areaListRv.setItemAnimator(new DefaultItemAnimator());
        dialogBuilder.setCancelable(false);

        spsEditText = dialogBuilder.findViewById(R.id.spEditText);

        spsEditText.setHint(getString(R.string.search_city));
        etSearch.setHint(getString(R.string.search_city));
        cancelBt.setText(getString(R.string.cancel));
        doneBtn.setText(getString(R.string.done));
        tv_title_no_data.setText(getString(R.string.no_city_found));



        doneBtn.setOnClickListener(v -> {
            if (!Objects.equals(TempcityId, "-1")) {
                dialogBuilder.dismiss();
                cityId = TempcityId;
                cityName = TempcityName;

                filterActivityTvCity.setText("" + cityName);
            } else {
                BasicFunctions.toast(FilterActivity.this, getString(R.string.please_select_city), 1);
            }

        });

        cancelBt.setOnClickListener(v -> dialogBuilder.dismiss());

        dialogBuilder.show();
        if (city != null) {

            adaptercity = new SpinAdapter(FilterActivity.this, city, Integer.parseInt(cityId), cityId);


            adaptercity.setOnItemClickListener(new SpinAdapter.AreaSingleClickListener() {
                @Override
                public void onItemClickListener(String name, String id, int position, LocationHelper help) {
                    adaptercity.selectedItem();
                    TempcityId = id;
                    TempcityName = name;
                    BasicFunctions.hideSoftKeyboard(FilterActivity.this);
                }
            });
            areaListRv.setAdapter(adaptercity);

        }

        spsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String newText = s.toString();
                if (city != null) {
                    List<LocationHelper> cityFilter = new ArrayList<>();

                    if (newText.length() > 0) {
                        for (int i = 0; i < city.size(); i++) {

                            if (city.get(i).name.toLowerCase().contains(newText.toLowerCase())
                                    || city.get(i).nameSearch.toLowerCase().contains(newText.toLowerCase())) {
                                cityFilter.add(city.get(i));
                            }

                        }
                        if (cityFilter.size() > 0)
                            linNoDataFound.setVisibility(View.GONE);
                        else
                            linNoDataFound.setVisibility(View.VISIBLE);
                    } else {
                        cityFilter = city;
                        linNoDataFound.setVisibility(View.GONE);
                    }
                    adaptercity.updateSearch(cityFilter);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().trim();
                if (city != null) {
                    List<LocationHelper> cityFilter = new ArrayList<>();

                    if (newText.length() > 0) {
                        for (int i = 0; i < city.size(); i++) {

                            if (city.get(i).name.toLowerCase().contains(newText.toLowerCase())
                                    || city.get(i).nameSearch.toLowerCase().contains(newText.toLowerCase())) {
                                cityFilter.add(city.get(i));
                            }

                        }
                        if (cityFilter.size() > 0)
                            linNoDataFound.setVisibility(View.GONE);
                        else
                            linNoDataFound.setVisibility(View.VISIBLE);
                    } else {
                        cityFilter = city;
                        linNoDataFound.setVisibility(View.GONE);
                    }
                    adaptercity.updateSearch(cityFilter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick(R.id.filterActivityBtnNext)
    public void click() {

        if (countryId.equalsIgnoreCase("-1")) {
            BasicFunctions.toast(FilterActivity.this, getString(R.string.select_country), 1);
        } else if (stateId.equalsIgnoreCase("-1")) {
            BasicFunctions.toast(FilterActivity.this, getString(R.string.select_state), 1);
        } else if (cityId.equalsIgnoreCase("-1")) {
            BasicFunctions.toast(FilterActivity.this, getString(R.string.select_city), 1);
        } else {

            Intent intent = new Intent(FilterActivity.this, SelectSocietyActivity.class);
            intent.putExtra("countryId", countryId);
            intent.putExtra("stateId", stateId);
            intent.putExtra("cityId", cityId);
            intent.putExtra("isFromSetting", false);
            intent.putExtra("countryCode", countryCode);
            startActivity(intent);
            filterActivityBtnNext.setClickable(false);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        filterActivityBtnNext.setClickable(true);
    }

    public void ClearState() {

        if (states != null) {
            stateId = "-1";
            TempstateId = "-1";
            stateName = "";
            states.clear();

            filterActivityTvState.setText(getString(R.string.select_state));


        }

    }

    public void ClearCity() {

        if (city != null) {
            cityId = "-1";
            TempcityId = "-1";
            cityName = "";
            city.clear();

            filterActivityTvCity.setText(getString(R.string.select_city));

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(mybroadcast);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void stateChanged() {
        try {
            if (!FilterActivity.this.isDestroyed() && !FilterActivity.this.isFinishing()) {

                boolean modelState = ConnectivityListner.getInstance().getState();

                if (modelState) {

                    if (dialog != null) {
                        dialog.cancel();
                    }

                    initCode();

                } else {

                    if (dialog != null) {
                        dialog.show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void showVPNDialog() {


        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.vpn_connection))
                .setPositiveButton("Retry", (dialog, which) -> reload())
                .setIcon(android.R.drawable.ic_dialog_alert);

    }
}

