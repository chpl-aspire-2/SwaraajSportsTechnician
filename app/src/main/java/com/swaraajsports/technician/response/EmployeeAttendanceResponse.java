package com.swaraajsports.technician.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EmployeeAttendanceResponse implements Serializable {

    @SerializedName("empAttend")
    @Expose
    private List<EmpAttend> empAttend = null;
    @SerializedName("summary")
    @Expose
    private List<Summary> summary = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("month_list")
    @Expose
    private List<Month> monthList = null;


    public List<Month> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<Month> monthList) {
        this.monthList = monthList;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<EmpAttend> getEmpAttend() {
        return empAttend;
    }

    public void setEmpAttend(List<EmpAttend> empAttend) {
        this.empAttend = empAttend;
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

    public List<Summary> getSummary() {
        return summary;
    }

    public void setSummary(List<Summary> summary) {
        this.summary = summary;
    }

    public class Summary implements Serializable{
        @SerializedName("month_name")
        @Expose
        private String monthName;
        @SerializedName("total_working_days")
        @Expose
        private String totalWorkingDays;
        @SerializedName("absunt_days")
        @Expose
        private String absuntDays;
        @SerializedName("totalDaysMonth")
        @Expose
        private String totalDaysMonth;

        public String getMonthName() {
            return monthName;
        }

        public void setMonthName(String monthName) {
            this.monthName = monthName;
        }

        public String getTotalWorkingDays() {
            return totalWorkingDays;
        }

        public void setTotalWorkingDays(String totalWorkingDays) {
            this.totalWorkingDays = totalWorkingDays;
        }

        public String getAbsuntDays() {
            return absuntDays;
        }

        public void setAbsuntDays(String absuntDays) {
            this.absuntDays = absuntDays;
        }

        public String getTotalDaysMonth() {
            return totalDaysMonth;
        }

        public void setTotalDaysMonth(String totalDaysMonth) {
            this.totalDaysMonth = totalDaysMonth;
        }
    }

    public class InOutTime implements Serializable{

        @SerializedName("int_time")
        @Expose
        private String intTime;
        @SerializedName("out_time")
        @Expose
        private String outTime;
        @SerializedName("time_slot")
        @Expose
        private String time_slot;
        @SerializedName("visit_date")
        @Expose
        private String visit_date;
        @SerializedName("exit_date")
        @Expose
        private String exit_date;

        public String getTime_slot() {
            return time_slot;
        }

        public void setTime_slot(String time_slot) {
            this.time_slot = time_slot;
        }

        public String getVisit_date() {
            return visit_date;
        }

        public void setVisit_date(String visit_date) {
            this.visit_date = visit_date;
        }

        public String getExit_date() {
            return exit_date;
        }

        public void setExit_date(String exit_date) {
            this.exit_date = exit_date;
        }

        public String getIntTime() {
            return intTime;
        }

        public void setIntTime(String intTime) {
            this.intTime = intTime;
        }

        public String getOutTime() {
            return outTime;
        }

        public void setOutTime(String outTime) {
            this.outTime = outTime;
        }
    }

    public class EmpAttend implements Serializable{

        @SerializedName("emp_id")
        @Expose
        private String empId;
        @SerializedName("society_id")
        @Expose
        private String societyId;
        @SerializedName("att_date")
        @Expose
        private String attDate;
        @SerializedName("isPresent")
        @Expose
        private Boolean isPresent;
        @SerializedName("in_out_time")
        @Expose
        private List<InOutTime> inOutTime = null;

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        public String getSocietyId() {
            return societyId;
        }

        public void setSocietyId(String societyId) {
            this.societyId = societyId;
        }

        public String getAttDate() {
            return attDate;
        }

        public void setAttDate(String attDate) {
            this.attDate = attDate;
        }

        public Boolean getPresent() {
            return isPresent;
        }

        public void setPresent(Boolean present) {
            isPresent = present;
        }

        public List<InOutTime> getInOutTime() {
            return inOutTime;
        }

        public void setInOutTime(List<InOutTime> inOutTime) {
            this.inOutTime = inOutTime;
        }
    }

    public class Month implements Serializable{

        @SerializedName("month_name_view")
        @Expose
        private String monthNameView;
        @SerializedName("month_name")
        @Expose
        private String monthName;
        @SerializedName("current_month")
        @Expose
        private Boolean currentMonth;

        public String getMonthNameView() {
            return monthNameView;
        }

        public void setMonthNameView(String monthNameView) {
            this.monthNameView = monthNameView;
        }

        public String getMonthName() {
            return monthName;
        }

        public void setMonthName(String monthName) {
            this.monthName = monthName;
        }

        public Boolean getCurrentMonth() {
            return currentMonth;
        }

        public void setCurrentMonth(Boolean currentMonth) {
            this.currentMonth = currentMonth;
        }

    }
}
