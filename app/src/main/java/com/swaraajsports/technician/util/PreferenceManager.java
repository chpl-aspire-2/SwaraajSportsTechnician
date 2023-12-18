package com.swaraajsports.technician.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceManager {

    public static PreferenceManager preferenceManager;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    public PreferenceManager(Context context) {
        preferenceManager = this;
        mSharedPreferences = context.getSharedPreferences("scanner_app_association", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    public static void setPreferenceManager(PreferenceManager preferenceManager) {
        PreferenceManager.preferenceManager = preferenceManager;
    }

    public static PreferenceManager getInstance() {
        return preferenceManager;
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }


    public SharedPreferences.Editor getmEditor() {
        return mEditor;
    }

    public void setmEditor(SharedPreferences.Editor mEditor) {
        this.mEditor = mEditor;
    }

    public void clearPreferences() {
        mEditor.clear();
        mEditor.commit();
        //deleteLoginSession();
    }

    /*set preference for registration*/

    public void removePref(Context context, String keyToRemove) {
        mSharedPreferences = context.getSharedPreferences("scanner_app_association", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.remove(keyToRemove);
        mEditor.apply();
    }
    public String getSocietyId() {
        return mSharedPreferences.getString("association_id", "0");
    }

    public void setSocietyId(String id) {
        mEditor.putString("association_id", id).commit();
    }


    public void setLoginSession() {
        mEditor.putBoolean("login_session", true);
        mEditor.commit();
    }

    public boolean getLoginSession() {
        return mSharedPreferences.getBoolean("login_session", false);
    }


    public boolean getFirstSession() {
        return mSharedPreferences.getBoolean("first_start", false);
    }

    public void setFirstSession(boolean wiFiSession) {
        mEditor.putBoolean("first_start", wiFiSession).commit();
    }

    public void setApikey(String wiFiSession) {
        mEditor.putString("key", wiFiSession).commit();
    }

    public String getApiKey() {
        return mSharedPreferences.getString("key", "0");
    }

    public String getBaseUrl() {
        return mSharedPreferences.getString("baseurl", "") + VariableBag.SUB_URL;
        //return "http://192.168.0.101/fincasys/" + VariableBag.SUB_URL;
    }

    public void setBaseUrl(String wiFiSession) {
        mEditor.putString("baseurl", wiFiSession).commit();
    }

    public String getBgSplashUrl() {
        return mSharedPreferences.getString("bgUrl", "");
    }

    public void setBgSplashUrl(String wiFiSession) {
        mEditor.putString("bgUrl", wiFiSession).commit();
    }

    public String getBgSplashColorCode() {
        return mSharedPreferences.getString("bgColorCode", "");
    }

    public void setBgSplashColorCode(String wiFiSession) {
        mEditor.putString("bgColorCode", wiFiSession).commit();
    }

    public void setKeyValueString(String key, String value) {
        mEditor.putString(key, value).commit();
    }


    public void setKeyValueInt(String key, int value) {
        mEditor.putInt(key, value).commit();
    }

    public void setKeyValueBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).commit();
    }

    public String getKeyValueString(String key) {
        return mSharedPreferences.getString(key, " ");
    }

    public String getKeyValueStringWithZero(String key) {
        return mSharedPreferences.getString(key, "0");
    }

    public int getKeyValueInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public boolean getKeyValueBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void setUserFullName(String userFullName) {
        mEditor.putString("fullName", userFullName).commit();
    }

    public String getUserName() {
        return mSharedPreferences.getString("fullName", "");
    }


    public void setUserId(String userFullName) {
        mEditor.putString("user_id", userFullName).commit();
    }

    public String getUserId() {
        return mSharedPreferences.getString("user_id", "");
    }


    public String getCountryID() {
        return mSharedPreferences.getString(VariableBag.COUNTRY_ID, "0");
    }

    public void setCountryID(String id) {
        mEditor.putString(VariableBag.COUNTRY_ID, id).commit();
    }


    public String getStateID() {
        return mSharedPreferences.getString(VariableBag.STATE_ID, "0");
    }

    public void setStateID(String id) {
        mEditor.putString(VariableBag.STATE_ID, id).commit();
    }


    public String getCityID() {
        return mSharedPreferences.getString(VariableBag.CITY_ID, "0");
    }

    public void setCityID(String id) {
        mEditor.putString(VariableBag.CITY_ID, id).commit();
    }


    public String getSocietyName() {
        return mSharedPreferences.getString(VariableBag.SOCIETY_NAME, "0");
    }

    public void setSocietyName(String id) {
        mEditor.putString(VariableBag.SOCIETY_NAME, id).commit();
    }


}


