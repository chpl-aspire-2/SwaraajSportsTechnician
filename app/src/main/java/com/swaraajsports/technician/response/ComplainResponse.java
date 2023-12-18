package com.swaraajsports.technician.response;

import java.io.Serializable;
import java.util.List;

import com.swaraajsports.technician.activity.ComplaintHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ComplainResponse implements Serializable {

    @SerializedName("complain")
    @Expose
    public List<Complain> complain;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("complaint_category")
    @Expose
    private String complainsCategory;

    @SerializedName("block_message")
    @Expose
    private String blockMessage;


    @SerializedName("block_status")
    @Expose
    private boolean blockStatus;


    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("audio_duration")
    @Expose
    private int audioDuration;
    @SerializedName("track")
    @Expose
    private List<Track> track = null;

    @SerializedName("complain_status_array")
    @Expose
    private List<ComplaintHelper> complainStatusArray;


    public List<ComplaintHelper> getComplainStatusArray() {
        return complainStatusArray;
    }

    public void setComplainStatusArray(List<ComplaintHelper> complainStatusArray) {
        this.complainStatusArray = complainStatusArray;
    }

    public String getComplainsCategory() {
        return complainsCategory;
    }

    public void setComplainsCategory(String complainsCategory) {
        this.complainsCategory = complainsCategory;
    }

    public String getBlockMessage() {
        return blockMessage;
    }

    public void setBlockMessage(String blockMessage) {
        this.blockMessage = blockMessage;
    }

    public boolean isBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(boolean blockStatus) {
        this.blockStatus = blockStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(int audioDuration) {
        this.audioDuration = audioDuration;
    }

    public List<Track> getTrack() {
        return track;
    }

    public void setTrack(List<Track> track) {
        this.track = track;
    }

    public List<Complain> getComplain() {
        return complain;
    }

    public void setComplain(List<Complain> complain) {
        this.complain = complain;
    }

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

    public static class Complain implements Serializable{
        @SerializedName("complain_id")
        @Expose
        public String complain_id;
        @SerializedName("user_id")
        @Expose
        public String user_id;
        @SerializedName("society_id")
        @Expose
        public String society_id;
        @SerializedName("complain_no")
        @Expose
        public String complain_no;
        @SerializedName("complaint_category")
        @Expose
        public String complaint_category;
        @SerializedName("complaint_category_view")
        @Expose
        public String complaint_category_view;
        @SerializedName("compalain_title")
        @Expose
        public String compalain_title;
        @SerializedName("complain_description")
        @Expose
        public String complain_description;
        @SerializedName("complain_photo")
        @Expose
        private String complainPhoto;
        @SerializedName("complain_date")
        @Expose
        public String complain_date;
        @SerializedName("complain_status")
        @Expose
        public String complain_status;
        @SerializedName("complain_status_view")
        @Expose
        public String complain_status_view;
        @SerializedName("user_full_name")
        @Expose
        public String user_full_name;
        @SerializedName("complain_assing_to")
        @Expose
        public String complain_assing_to;
        @SerializedName("track")
        @Expose
        public List<Track> track;
        @SerializedName("complain_review_msg")
        @Expose
        public String complain_review_msg;
        @SerializedName("rating_star")
        @Expose
        public String rating_star;
        @SerializedName("feedback_msg")
        @Expose
        public String feedback_msg;
        @SerializedName("technician")
        @Expose
        public String technician;

        @SerializedName("auto_close")
        @Expose
        public boolean autoClose;

        public boolean isAutoClose() {
            return autoClose;
        }

        public void setAutoClose(boolean autoClose) {
            this.autoClose = autoClose;
        }

        public String getComplain_id() {
            return complain_id;
        }

        public void setComplain_id(String complain_id) {
            this.complain_id = complain_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSociety_id() {
            return society_id;
        }

        public void setSociety_id(String society_id) {
            this.society_id = society_id;
        }

        public String getComplain_no() {
            return complain_no;
        }

        public void setComplain_no(String complain_no) {
            this.complain_no = complain_no;
        }

        public String getComplainPhoto() {
            return complainPhoto;
        }

        public void setComplainPhoto(String complainPhoto) {
            this.complainPhoto = complainPhoto;
        }

        public String getComplaint_category() {
            return complaint_category;
        }

        public void setComplaint_category(String complaint_category) {
            this.complaint_category = complaint_category;
        }

        public String getComplaint_category_view() {
            return complaint_category_view;
        }

        public void setComplaint_category_view(String complaint_category_view) {
            this.complaint_category_view = complaint_category_view;
        }

        public String getCompalain_title() {
            return compalain_title;
        }

        public void setCompalain_title(String compalain_title) {
            this.compalain_title = compalain_title;
        }

        public String getComplain_description() {
            return complain_description;
        }

        public void setComplain_description(String complain_description) {
            this.complain_description = complain_description;
        }

        public String getComplain_date() {
            return complain_date;
        }

        public void setComplain_date(String complain_date) {
            this.complain_date = complain_date;
        }

        public String getComplain_status() {
            return complain_status;
        }

        public void setComplain_status(String complain_status) {
            this.complain_status = complain_status;
        }

        public String getComplain_status_view() {
            return complain_status_view;
        }

        public void setComplain_status_view(String complain_status_view) {
            this.complain_status_view = complain_status_view;
        }

        public String getUser_full_name() {
            return user_full_name;
        }

        public void setUser_full_name(String user_full_name) {
            this.user_full_name = user_full_name;
        }

        public String getComplain_assing_to() {
            return complain_assing_to;
        }

        public void setComplain_assing_to(String complain_assing_to) {
            this.complain_assing_to = complain_assing_to;
        }

        public List<Track> getTrack() {
            return track;
        }

        public void setTrack(List<Track> track) {
            this.track = track;
        }

        public String getComplain_review_msg() {
            return complain_review_msg;
        }

        public void setComplain_review_msg(String complain_review_msg) {
            this.complain_review_msg = complain_review_msg;
        }

        public String getRating_star() {
            return rating_star;
        }

        public void setRating_star(String rating_star) {
            this.rating_star = rating_star;
        }

        public String getFeedback_msg() {
            return feedback_msg;
        }

        public void setFeedback_msg(String feedback_msg) {
            this.feedback_msg = feedback_msg;
        }

        public String getTechnician() {
            return technician;
        }

        public void setTechnician(String technician) {
            this.technician = technician;
        }
    }

    public static class Track implements Serializable{
        @SerializedName("complains_track_id")
        @Expose
        public String complains_track_id;
        @SerializedName("complains_track_by")
        @Expose
        public String complains_track_by;
        @SerializedName("complains_track_msg")
        @Expose
        public String complains_track_msg;
        @SerializedName("complains_track_voice")
        @Expose
        public String complains_track_voice;
        @SerializedName("complains_track_img")
        @Expose
        public String complains_track_img;
        @SerializedName("complain_id")
        @Expose
        public String complain_id;
        @SerializedName("admin_id")
        @Expose
        public String admin_id;
        @SerializedName("complains_track_date_time")
        @Expose
        public String complains_track_date_time;
        @SerializedName("complain_photo_old")
        @Expose
        public String complain_photo_old;
        @SerializedName("complaint_voice_old")
        @Expose
        public String complaint_voice_old;
        @SerializedName("msg_date")
        @Expose
        private String msg_date;
        @SerializedName("isDate")
        @Expose
        private boolean isDate;
        @SerializedName("msg_date_view")
        @Expose
        private String msg_date_view;
        @SerializedName("complaint_status_int")
        @Expose
        private String complaint_status_int;
        @SerializedName("complaint_status_view")
        @Expose
        private String complaintStatusView;
        @SerializedName("admin_name")
        @Expose
        private String adminName;

        public String getAdminName() {
            return adminName;
        }

        public void setAdminName(String adminName) {
            this.adminName = adminName;
        }

        public String getComplaintStatusView() {
            return complaintStatusView;
        }

        public void setComplaintStatusView(String complaintStatusView) {
            this.complaintStatusView = complaintStatusView;
        }

        public String getComplaint_status_int() {
            return complaint_status_int;
        }

        public void setComplaint_status_int(String complaint_status_int) {
            this.complaint_status_int = complaint_status_int;
        }

        public String getMsg_date() {
            return msg_date;
        }

        public void setMsg_date(String msg_date) {
            this.msg_date = msg_date;
        }

        public boolean isDate() {
            return isDate;
        }

        public void setDate(boolean date) {
            isDate = date;
        }

        public String getMsg_date_view() {
            return msg_date_view;
        }

        public void setMsg_date_view(String msg_date_view) {
            this.msg_date_view = msg_date_view;
        }

        public String getComplains_track_id() {
            return complains_track_id;
        }

        public void setComplains_track_id(String complains_track_id) {
            this.complains_track_id = complains_track_id;
        }

        public String getComplains_track_by() {
            return complains_track_by;
        }

        public void setComplains_track_by(String complains_track_by) {
            this.complains_track_by = complains_track_by;
        }

        public String getComplains_track_msg() {
            return complains_track_msg;
        }

        public void setComplains_track_msg(String complains_track_msg) {
            this.complains_track_msg = complains_track_msg;
        }

        public String getComplains_track_voice() {
            return complains_track_voice;
        }

        public void setComplains_track_voice(String complains_track_voice) {
            this.complains_track_voice = complains_track_voice;
        }

        public String getComplains_track_img() {
            return complains_track_img;
        }

        public void setComplains_track_img(String complains_track_img) {
            this.complains_track_img = complains_track_img;
        }

        public String getComplain_id() {
            return complain_id;
        }

        public void setComplain_id(String complain_id) {
            this.complain_id = complain_id;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getComplains_track_date_time() {
            return complains_track_date_time;
        }

        public void setComplains_track_date_time(String complains_track_date_time) {
            this.complains_track_date_time = complains_track_date_time;
        }

        public String getComplain_photo_old() {
            return complain_photo_old;
        }

        public void setComplain_photo_old(String complain_photo_old) {
            this.complain_photo_old = complain_photo_old;
        }

        public String getComplaint_voice_old() {
            return complaint_voice_old;
        }

        public void setComplaint_voice_old(String complaint_voice_old) {
            this.complaint_voice_old = complaint_voice_old;
        }
    }
}
