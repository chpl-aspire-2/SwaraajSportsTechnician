package com.swaraajsports.technician.response;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardResponse implements Serializable {

    @SerializedName("categroy")
    @Expose
    private List<Categroy> categroy = null;

    @SerializedName("maintenance")
    @Expose
    private List<AssetDetailResponse.Maintenance> maintenance = null;

    @SerializedName("emp_name")
    @Expose
    private String emp_name;
    @SerializedName("emp_profile")
    @Expose
    private String emp_profile;
    @SerializedName("auto_close_time")
    @Expose
    private String auto_close_time;
    @SerializedName("emp_mobile")
    @Expose
    private String emp_mobile;
    @SerializedName("country_code")
    @Expose
    private String country_code;
    @SerializedName("emp_email")
    @Expose
    private String emp_email;
    @SerializedName("emp_address")
    @Expose
    private String emp_address;
    @SerializedName("emp_date_of_joing")
    @Expose
    private String emp_date_of_joing;
    @SerializedName("emp_sallary")
    @Expose
    private String emp_sallary;
    @SerializedName("open_status")
    @Expose
    private String open_status;
    @SerializedName("hold_status")
    @Expose
    private String hold_status;
    @SerializedName("close_status")
    @Expose
    private String close_status;
    @SerializedName("reopen_status")
    @Expose
    private String reopen_status;
    @SerializedName("inprogress_status")
    @Expose
    private String inprogress_status;
    @SerializedName("open_complain")
    @Expose
    private String open_complain;
    @SerializedName("inprogress_complain")
    @Expose
    private String inprogress_complain;
    @SerializedName("hold_complain")
    @Expose
    private String hold_complain;
    @SerializedName("close_complain")
    @Expose
    private String close_complain;
    @SerializedName("re_pen_complain")
    @Expose
    private String re_pen_complain;
    @SerializedName("all_complain")
    @Expose
    private String all_complain;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("unread_notification")
    @Expose
    private String readStatus;

    @SerializedName("total_day_in_month")
    @Expose
    private String total_day_in_month;
    @SerializedName("this_month_present")
    @Expose
    private String this_month_present;
    @SerializedName("this_week_present")
    @Expose
    private String this_week_present;
    @SerializedName("this_month_name")
    @Expose
    private String this_month_name;



    @SerializedName("complain")
    @Expose
    public List<ComplainResponse.Complain> complain;


    public List<AssetDetailResponse.Maintenance> getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(List<AssetDetailResponse.Maintenance> maintenance) {
        this.maintenance = maintenance;
    }

    public List<ComplainResponse.Complain> getComplain() {
        return complain;
    }

    public void setComplain(List<ComplainResponse.Complain> complain) {
        this.complain = complain;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_profile() {
        return emp_profile;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public List<Categroy> getCategroy() {
        return categroy;
    }

    public String getHold_status() {
        return hold_status;
    }

    public void setHold_status(String hold_status) {
        this.hold_status = hold_status;
    }

    public String getHold_complain() {
        return hold_complain;
    }

    public void setHold_complain(String hold_complain) {
        this.hold_complain = hold_complain;
    }

    public void setCategroy(List<Categroy> categroy) {
        this.categroy = categroy;
    }

    public String getAuto_close_time() {
        return auto_close_time;
    }

    public String getThis_month_name() {
        return this_month_name;
    }

    public void setThis_month_name(String this_month_name) {
        this.this_month_name = this_month_name;
    }

    public void setAuto_close_time(String auto_close_time) {
        this.auto_close_time = auto_close_time;
    }

    public String getTotal_day_in_month() {
        return total_day_in_month;
    }

    public void setTotal_day_in_month(String total_day_in_month) {
        this.total_day_in_month = total_day_in_month;
    }

    public String getThis_month_present() {
        return this_month_present;
    }

    public void setThis_month_present(String this_month_present) {
        this.this_month_present = this_month_present;
    }

    public String getThis_week_present() {
        return this_week_present;
    }

    public void setThis_week_present(String this_week_present) {
        this.this_week_present = this_week_present;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public void setEmp_profile(String emp_profile) {
        this.emp_profile = emp_profile;
    }

    public String getEmp_mobile() {
        return emp_mobile;
    }

    public void setEmp_mobile(String emp_mobile) {
        this.emp_mobile = emp_mobile;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public String getEmp_address() {
        return emp_address;
    }

    public void setEmp_address(String emp_address) {
        this.emp_address = emp_address;
    }

    public String getEmp_date_of_joing() {
        return emp_date_of_joing;
    }

    public void setEmp_date_of_joing(String emp_date_of_joing) {
        this.emp_date_of_joing = emp_date_of_joing;
    }

    public String getEmp_sallary() {
        return emp_sallary;
    }

    public void setEmp_sallary(String emp_sallary) {
        this.emp_sallary = emp_sallary;
    }

    public String getOpen_status() {
        return open_status;
    }

    public void setOpen_status(String open_status) {
        this.open_status = open_status;
    }

    public String getClose_status() {
        return close_status;
    }

    public void setClose_status(String close_status) {
        this.close_status = close_status;
    }

    public String getReopen_status() {
        return reopen_status;
    }

    public void setReopen_status(String reopen_status) {
        this.reopen_status = reopen_status;
    }

    public String getInprogress_status() {
        return inprogress_status;
    }

    public void setInprogress_status(String inprogress_status) {
        this.inprogress_status = inprogress_status;
    }

    public String getOpen_complain() {
        return open_complain;
    }

    public void setOpen_complain(String open_complain) {
        this.open_complain = open_complain;
    }

    public String getInprogress_complain() {
        return inprogress_complain;
    }

    public void setInprogress_complain(String inprogress_complain) {
        this.inprogress_complain = inprogress_complain;
    }

    public String getClose_complain() {
        return close_complain;
    }

    public void setClose_complain(String close_complain) {
        this.close_complain = close_complain;
    }

    public String getRe_pen_complain() {
        return re_pen_complain;
    }

    public void setRe_pen_complain(String re_pen_complain) {
        this.re_pen_complain = re_pen_complain;
    }

    public String getAll_complain() {
        return all_complain;
    }

    public void setAll_complain(String all_complain) {
        this.all_complain = all_complain;
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

    public class Categroy implements Serializable{

        @SerializedName("category_name")
        @Expose
        private String categoryName;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

    }
}
