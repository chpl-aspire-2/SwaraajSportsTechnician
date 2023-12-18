package com.swaraajsports.technician.response;

public class WorkingMonthHelper {

    private String monthName;
    private int month;
    private int year;

    public WorkingMonthHelper() {

    }

    public WorkingMonthHelper(String monthName, int month, int year) {
        this.monthName = monthName;
        this.month = month;
        this.year = year;
    }


    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
