package com.swaraajsports.technician.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NotificationResponce implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("notification")
    @Expose
    private List<Notification> notification = null;

    @SerializedName("read_status")
    @Expose
    private String readStatus;
    @SerializedName("visitor_in")
    @Expose
    private String visitor_in;
    @SerializedName("visitor_out")
    @Expose
    private String visitor_out;



    public String getMessage() {
        return message;
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

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

    public String getVisitor_in() {
        return visitor_in;
    }

    public void setVisitor_in(String visitor_in) {
        this.visitor_in = visitor_in;
    }

    public String getVisitor_out() {
        return visitor_out;
    }

    public void setVisitor_out(String visitor_out) {
        this.visitor_out = visitor_out;
    }

    public class Notification {

        @SerializedName("guard_notification_id")
        @Expose
        private String guardNotificationId;
        @SerializedName("guard_notification_title")
        @Expose
        private String guardNotificationTitle;
        @SerializedName("guard_notification_desc")
        @Expose
        private String guardNotificationDesc;
        @SerializedName("employee_id")
        @Expose
        private String employeeId;
        @SerializedName("guard_notification_date")
        @Expose
        private String guardNotificationDate;

        @SerializedName("click_action")
        @Expose
        private String click_action;

        @SerializedName("notification_logo")
        @Expose
        private String notificationLogo;

        public String getGuardNotificationId() {
            return guardNotificationId;
        }

        public void setGuardNotificationId(String guardNotificationId) {
            this.guardNotificationId = guardNotificationId;
        }

        public String getGuardNotificationTitle() {
            return guardNotificationTitle;
        }

        public void setGuardNotificationTitle(String guardNotificationTitle) {
            this.guardNotificationTitle = guardNotificationTitle;
        }

        public String getGuardNotificationDesc() {
            return guardNotificationDesc;
        }

        public void setGuardNotificationDesc(String guardNotificationDesc) {
            this.guardNotificationDesc = guardNotificationDesc;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public String getGuardNotificationDate() {
            return guardNotificationDate;
        }

        public void setGuardNotificationDate(String guardNotificationDate) {
            this.guardNotificationDate = guardNotificationDate;
        }

        public String getClick_action() {
            return click_action;
        }

        public void setClick_action(String click_action) {
            this.click_action = click_action;
        }

        public String getNotificationLogo() {
            return notificationLogo;
        }

        public void setNotificationLogo(String notificationLogo) {
            this.notificationLogo = notificationLogo;
        }
    }

    }
