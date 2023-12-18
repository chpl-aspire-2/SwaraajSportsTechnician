package com.swaraajsports.technician.activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ComplaintHelper implements Serializable {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("value")
    @Expose
    String value;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
