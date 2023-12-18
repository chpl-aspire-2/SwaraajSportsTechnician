package com.swaraajsports.technician.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SocietyResponse implements Serializable {

    @SerializedName("society")
    @Expose
    private List<Society> society = null;
    @SerializedName("status")
    @Expose
    private
    String status;
    @SerializedName("message")
    @Expose
    private
    String message;
    @SerializedName("take_request_society")
    @Expose
    private
    boolean take_request_society;

    public boolean isTake_request_society() {
        return take_request_society;
    }

    public void setTake_request_society(boolean take_request_society) {
        this.take_request_society = take_request_society;
    }

    public List<Society> getSociety() {
        return society;
    }

    public void setSociety(List<Society> society) {
        this.society = society;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String success) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Society implements Serializable {

        @SerializedName("society_id")
        @Expose
        private String societyId;
        @SerializedName("societyUserId")
        @Expose
        private String societyUserId;
        @SerializedName("society_name")
        @Expose
        private String societyName;
        @SerializedName("society_address")
        @Expose
        private String societyAddress;
        @SerializedName("secretary_email")
        @Expose
        private String secretaryEmail;
        @SerializedName("secretary_mobile")
        @Expose
        private String secretaryMobile;
        @SerializedName("socieaty_logo")
        @Expose
        private String socieatyLogo;
        @SerializedName("builder_name")
        @Expose
        private String builderName;
        @SerializedName("builder_address")
        @Expose
        private String builderAddress;
        @SerializedName("builder_mobile")
        @Expose
        private String builderMobile;
        @SerializedName("socieaty_status")
        @Expose
        private String socieatyStatus;

        @SerializedName("sub_domain")
        @Expose
        private String subDomain;

        @SerializedName("is_society")
        @Expose
        private boolean is_society;

        @SerializedName("api_key")
        @Expose
        private String apiKey;

        @SerializedName("is_firebase")
        @Expose
        private boolean isFirebase;

        private boolean isClicked = false;

        public boolean isClicked() {
            return isClicked;
        }

        public void setClicked(boolean clicked) {
            isClicked = clicked;
        }

        public String getSubDomain() {
            return subDomain;
        }

        public void setSubDomain(String subDomain) {
            this.subDomain = subDomain;
        }

        public boolean isIs_society() {
            return is_society;
        }

        public void setIs_society(boolean is_society) {
            this.is_society = is_society;
        }

        public String getApiKey() {
            return apiKey;
        }

        public boolean isFirebase() {
            return isFirebase;
        }

        public void setFirebase(boolean firebase) {
            isFirebase = firebase;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getSocietyId() {
            return societyId;
        }

        public void setSocietyId(String societyId) {
            this.societyId = societyId;
        }

        public String getSocietyUserId() {
            return societyUserId;
        }

        public void setSocietyUserId(String societyUserId) {
            this.societyUserId = societyUserId;
        }

        public String getSocietyName() {
            return societyName;
        }

        public void setSocietyName(String societyName) {
            this.societyName = societyName;
        }

        public String getSocietyAddress() {
            return societyAddress;
        }

        public void setSocietyAddress(String societyAddress) {
            this.societyAddress = societyAddress;
        }

        public String getSecretaryEmail() {
            return secretaryEmail;
        }

        public void setSecretaryEmail(String secretaryEmail) {
            this.secretaryEmail = secretaryEmail;
        }

        public String getSecretaryMobile() {
            return secretaryMobile;
        }

        public void setSecretaryMobile(String secretaryMobile) {
            this.secretaryMobile = secretaryMobile;
        }

        public String getSocieatyLogo() {
            return socieatyLogo;
        }

        public void setSocieatyLogo(String socieatyLogo) {
            this.socieatyLogo = socieatyLogo;
        }

        public String getBuilderName() {
            return builderName;
        }

        public void setBuilderName(String builderName) {
            this.builderName = builderName;
        }

        public String getBuilderAddress() {
            return builderAddress;
        }

        public void setBuilderAddress(String builderAddress) {
            this.builderAddress = builderAddress;
        }

        public String getBuilderMobile() {
            return builderMobile;
        }

        public void setBuilderMobile(String builderMobile) {
            this.builderMobile = builderMobile;
        }

        public String getSocieatyStatus() {
            return socieatyStatus;
        }

        public void setSocieatyStatus(String socieatyStatus) {
            this.socieatyStatus = socieatyStatus;
        }
    }
}
