package com.swaraajsports.technician.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommonResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private
    String status;
    @SerializedName("message")
    @Expose
    private
    String message;
    @SerializedName("availableBalance")
    @Expose
    private
    String availableBalance;
    @SerializedName("is_firebase")
    @Expose
    private
    boolean isFirebase;
    @SerializedName("is_email_otp")
    @Expose
    private
    boolean is_email_otp;
    @SerializedName("is_voice_otp")
    @Expose
    private
    boolean isVoiceOtp;

    public boolean isIs_email_otp() {
        return is_email_otp;
    }

    public void setIs_email_otp(boolean is_email_otp) {
        this.is_email_otp = is_email_otp;
    }

    public boolean isVoiceOtp() {
        return isVoiceOtp;
    }

    public void setVoiceOtp(boolean voiceOtp) {
        isVoiceOtp = voiceOtp;
    }

    public boolean isFirebase() {
        return isFirebase;
    }

    public void setFirebase(boolean firebase) {
        isFirebase = firebase;
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

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }
}
