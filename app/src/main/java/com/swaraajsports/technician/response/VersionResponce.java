package com.swaraajsports.technician.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VersionResponce implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("language_version")
    @Expose
    private String languageVersion;

    @SerializedName("version_id")
    @Expose
    private String version_id;

    @SerializedName("version_app")
    @Expose
    private String version_app;

    @SerializedName("version_code")
    @Expose
    private String version_code;

    @SerializedName("version_name")
    @Expose
    private String version_name;

    @SerializedName("back_banner")
    @Expose
    private String back_banner;

    @SerializedName("package_get_gatekeeper")
    @Expose
    private boolean package_get_gatekeeper;

    public String getBack_banner() {
        return back_banner;
    }

    public void setBack_banner(String back_banner) {
        this.back_banner = back_banner;
    }

    public String getMessage() {
        return message;
    }

    public boolean isPackage_get_gatekeeper() {
        return package_get_gatekeeper;
    }

    public void setPackage_get_gatekeeper(boolean package_get_gatekeeper) {
        this.package_get_gatekeeper = package_get_gatekeeper;
    }

    public String getLanguageVersion() {
        return languageVersion;
    }

    public void setLanguageVersion(String languageVersion) {
        this.languageVersion = languageVersion;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion_id() {
        return version_id;
    }

    public void setVersion_id(String version_id) {
        this.version_id = version_id;
    }

    public String getVersion_app() {
        return version_app;
    }

    public void setVersion_app(String version_app) {
        this.version_app = version_app;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }
}
