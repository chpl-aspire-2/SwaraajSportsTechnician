package com.swaraajsports.technician.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventListResponce implements Serializable{

    @SerializedName("event")
    @Expose
    private List<EventList> event_list = null;

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

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

    public List<EventList> getEvent_list() {
        return event_list;
    }

    public void setEvent_list(List<EventList> event_list) {
        this.event_list = event_list;
    }

    public class EventList implements Serializable {

        @SerializedName("hide_status")
        @Expose
        private String hideStatus;
        @SerializedName("event_id")
        @Expose
        private String eventId;
        @SerializedName("event_title")
        @Expose
        private String eventTitle;
        @SerializedName("event_image")
        @Expose
        private String eventImage;
        @SerializedName("event_upcoming_completed")
        @Expose
        private String eventUpcomingCompleted;
        @SerializedName("event_start_date")
        @Expose
        private String eventStartDate;
        @SerializedName("event_start_date_view")
        @Expose
        private String eventStartDateView;
        @SerializedName("event_end_date")
        @Expose
        private String eventEndDate;
        @SerializedName("event_location")
        @Expose
        private String eventLocation;
        @SerializedName("event_type")
        @Expose
        private String eventType;
        @SerializedName("totalPass")
        @Expose
        private String totalPass;
        @SerializedName("pendingPass")
        @Expose
        private String pendingPass;
        @SerializedName("VerifiedPass")
        @Expose
        private String VerifiedPass;

        public String getTotalPass() {
            return totalPass;
        }

        public void setTotalPass(String totalPass) {
            this.totalPass = totalPass;
        }

        public String getPendingPass() {
            return pendingPass;
        }

        public void setPendingPass(String pendingPass) {
            this.pendingPass = pendingPass;
        }

        public String getVerifiedPass() {
            return VerifiedPass;
        }

        public void setVerifiedPass(String verifiedPass) {
            VerifiedPass = verifiedPass;
        }

        private boolean isClicked = false;


        public String getHideStatus() {
            return hideStatus;
        }

        public void setHideStatus(String hideStatus) {
            this.hideStatus = hideStatus;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getEventTitle() {
            return eventTitle;
        }

        public void setEventTitle(String eventTitle) {
            this.eventTitle = eventTitle;
        }

        public String getEventImage() {
            return eventImage;
        }

        public void setEventImage(String eventImage) {
            this.eventImage = eventImage;
        }

        public String getEventUpcomingCompleted() {
            return eventUpcomingCompleted;
        }

        public void setEventUpcomingCompleted(String eventUpcomingCompleted) {
            this.eventUpcomingCompleted = eventUpcomingCompleted;
        }

        public String getEventStartDate() {
            return eventStartDate;
        }

        public void setEventStartDate(String eventStartDate) {
            this.eventStartDate = eventStartDate;
        }

        public String getEventStartDateView() {
            return eventStartDateView;
        }

        public void setEventStartDateView(String eventStartDateView) {
            this.eventStartDateView = eventStartDateView;
        }

        public String getEventEndDate() {
            return eventEndDate;
        }

        public void setEventEndDate(String eventEndDate) {
            this.eventEndDate = eventEndDate;
        }

        public String getEventLocation() {
            return eventLocation;
        }

        public void setEventLocation(String eventLocation) {
            this.eventLocation = eventLocation;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public boolean isClicked() {
            return isClicked;
        }

        public void setClicked(boolean clicked) {
            isClicked = clicked;
        }
    }

}
