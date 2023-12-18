package com.swaraajsports.technician.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private
    String status;
    @SerializedName("message")
    @Expose
    private
    String message;
    @SerializedName("emp_id")
    @Expose
    private String userId;
    @SerializedName("emp_name")
    @Expose
    private String userName;
    @SerializedName("emp_profile")
    @Expose
    private String empProfile;
    @SerializedName("emp_mobile")
    @Expose
    private String userMobile;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("society_name")
    @Expose
    private String societyName;
    @SerializedName("country_id")
    @Expose
    private String countryId;


    public String getEmpProfile() {
        return empProfile;
    }

    public void setEmpProfile(String empProfile) {
        this.empProfile = empProfile;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
