package com.swaraajsports.technician.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ajitmaurya.basicsetup.network.RestClient;
import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.network.RestCall;
import com.swaraajsports.technician.util.PreferenceManager;
import com.swaraajsports.technician.util.VariableBag;
import com.jakewharton.threetenabp.AndroidThreeTen;

import butterknife.ButterKnife;
import io.paperdb.Paper;

public abstract class BaseActivityClass extends AppCompatActivity {

    public PreferenceManager preferenceManager;

    public RestCall callCommonUrl, callMainUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        getWindow().setStatusBarColor(ContextCompat.getColor(BaseActivityClass.this,R.color.colorPrimary));

        if (VariableBag.WHITE_LABEL_LIGHT_THEME) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        ButterKnife.bind(this);

        Paper.init(this);
        AndroidThreeTen.init(this);

        preferenceManager = new PreferenceManager(this);

        callCommonUrl = RestClient.createService(RestCall.class, VariableBag.COMMON_URL, VariableBag.MAIN_KEY);
        callMainUrl = RestClient.createService(RestCall.class, VariableBag.MAIN_URL, VariableBag.MAIN_KEY);


        onViewReady(savedInstanceState, getIntent());

    }

    public RestCall getCallSociety() {
        return RestClient.createService(RestCall.class, preferenceManager.getBaseUrl(),
                preferenceManager.getApiKey());
    }

    public RestCall getCallSocietyWithCustomValue(String baseUrl, String apiKey, String userId, String userMobile, String societyId) {
        return RestClient.createService(RestCall.class, baseUrl,
                apiKey,
                userId,
                BasicFunctions.getCurrentPassword(societyId,
                        userId,
                        userMobile));
    }

    public RestCall getCallSocietyWithCustomValue(String baseUrl, String apiKey) {
        return RestClient.createService(RestCall.class, baseUrl, apiKey);
    }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        //To be used by child activities
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    protected void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e);
        }
    }



    protected void showBackArrow() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }


    protected void showAlertDialog(String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(null);
        dialogBuilder.setIcon(R.mipmap.ic_launcher);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton(getString(R.string.ok), (dialog, which) -> dialog.cancel());

        dialogBuilder.setCancelable(false);
        dialogBuilder.show();
    }

    protected void showAlertDialog(String msg,int icon) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(null);
        dialogBuilder.setIcon(icon);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton(getString(R.string.ok), (dialog, which) -> dialog.cancel());

        dialogBuilder.setCancelable(false);
        dialogBuilder.show();
    }

    protected void showToast(String mToastMsg) {
        Toast.makeText(this, mToastMsg, Toast.LENGTH_LONG).show();
    }

    protected abstract int getContentView();

}


/*
public class MainActivity extends BaseClass
{
    @Override
    protected int getContentView() {
        return R.layout.main_activity;//your layout
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        //your code
        //get baseclass methods like this
        //showToast("hello");

    }
}*/
