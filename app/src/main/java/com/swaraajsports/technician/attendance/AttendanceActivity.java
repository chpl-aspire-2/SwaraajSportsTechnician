package com.swaraajsports.technician.attendance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.activity.BaseActivityClass;
import com.swaraajsports.technician.calender.materialcalendarview.CalendarDay;
import com.swaraajsports.technician.calender.materialcalendarview.CalendarMode;
import com.swaraajsports.technician.calender.materialcalendarview.DayViewDecorator;
import com.swaraajsports.technician.calender.materialcalendarview.DayViewFacade;
import com.swaraajsports.technician.calender.materialcalendarview.MaterialCalendarView;
import com.swaraajsports.technician.response.EmployeeAttendanceResponse;
import com.swaraajsports.technician.response.WorkingMonthHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.BindView;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class AttendanceActivity extends BaseActivityClass {

    @BindView(R.id.materialCalendarView)
    MaterialCalendarView widget;

    @BindView(R.id.tvNoData)
    TextView tvNoData;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recy_view)
    RecyclerView recy_view;

    @BindView(R.id.spin)
    AppCompatSpinner spin;

    @BindView(R.id.lin_summery)
    LinearLayout lin_summery;

    @BindView(R.id.tv_title_summery)
    TextView tv_title_summery;

    @BindView(R.id.recy_view_summery)
    RecyclerView recy_view_summery;

    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.tv_working_day)
    TextView tv_working_day;
    @BindView(R.id.tv_absunt_days)
    TextView tv_absunt_days;
    @BindView(R.id.tv_total_days)
    TextView tv_total_days;

    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv4)
    TextView tv4;

    @BindView(R.id.ivBack)
    ImageView ivBack;


    int year, month;

    Calendar c;

    String strSelectedDate, empId;
    HashSet<CalendarDay> calendarDayHashSetPresent;
    HashSet<CalendarDay> calendarDayHashSetAbsent;
    List<EmployeeAttendanceResponse.EmpAttend> attendanceList;
    AttendanceAdapter attendanceAdapter;
    List<WorkingMonthHelper> spinnerHelper;
    List<String> spinnerArrayMonths;


    @Override
    protected int getContentView() {
        return R.layout.activity_attendance;//your layout
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);


        empId = preferenceManager.getUserId();

        attendanceList = new ArrayList<>();


        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);

        calendarDayHashSetPresent = new HashSet<>();
        calendarDayHashSetAbsent = new HashSet<>();

        spinnerHelper = new ArrayList<>();

        month = c.get(Calendar.MONTH);

        ArrayAdapter<String> adapter;


        spinnerArrayMonths = new ArrayList<>();

        // 4 month logic
        if (month > 3) {
            int max_month = month - 4;

            for (int i = month; i > max_month; i--) {
                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[i] + " - " + year);
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[i], i, year));

            }

        }
        else {

            if (month == 0) {
                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[0] + " - " + year);
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[0], 0, year));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[11] + " - " + (year - 1));
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[11], 11, (year - 1)));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[10] + " - " + (year - 1));
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[10], 10, (year - 1)));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[9] + " - " + (year - 1));
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[9], 9, (year - 1)));

            } else if (month == 1) {
                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[1] + " - " + year);
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[1], 1, year));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[0] + " - " + year);
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[0], 0, year));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[11] + " - " + (year - 1));
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[11], 11, (year - 1)));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[10] + " - " + (year - 1));
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[10], 10, (year - 1)));


            } else if (month == 2) {
                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[2] + " - " + year);
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[2], 2, year));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[1] + " - " + year);
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[1], 1, year));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[0] + " - " + (year - 1));
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[0], 0, (year - 1)));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[11] + " - " + (year - 1));
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[11], 11, (year - 1)));


            } else {

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[3] + " - " + (year - 1));
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[3], 3, (year - 1)));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[2] + " - " + year);
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[2], 2, year));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[1] + " - " + year);
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[1], 1, year));

                spinnerArrayMonths.add(getResources().getStringArray(R.array.month1)[0] + " - " + year);
                spinnerHelper.add(new WorkingMonthHelper(getResources().getStringArray(R.array.month1)[0], 0, year));


            }

        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerArrayMonths);

        spin.setAdapter(adapter);


        setLisSpinner();

        widget.setClickable(false);

        widget.setTopbarVisible(false);

        widget.state().edit()
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .setMinimumDate(CalendarDay.from(spinnerHelper.get(spinnerHelper.size() - 1).getYear(), spinnerHelper.get(spinnerHelper.size() - 1).getMonth() + 1, 1))
                .setMaximumDate(CalendarDay.from(spinnerHelper.get(0).getYear(), spinnerHelper.get(0).getMonth() + 1, c.get(Calendar.DAY_OF_MONTH)))
                .setShowWeekDays(true)
                .commit();

        widget.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);
        widget.setCurrentDate(CalendarDay.from(year, month + 1, c.get(Calendar.DAY_OF_MONTH)));

        Log.e("$$$$", "onViewReady: " + year + " " + (month + 1) + " " + c.get(Calendar.DAY_OF_MONTH));

        widget.setOnMonthChangedListener((widget, date) -> {

            spin.setOnItemSelectedListener(null);

            for (int k = 0; k < spinnerHelper.size(); k++) {
                if (spinnerHelper.get(k).getMonth() == (date.getMonth() - 1)) {
                    spin.setSelection(k);
                    setLisSpinner();

                    calculateMonthLeave(spinnerHelper.get(k).getYear(), spinnerHelper.get(k).getMonth() + 1);

                    break;
                }
            }

        });

        widget.setOnDateChangedListener((widget, date, selected) -> {
            if (selected) {
                strSelectedDate = date.getDate().toString();
                if (attendanceList != null && attendanceList.size() > 0) {
                    for (int i = 0; i < attendanceList.size(); i++) {
                        if (strSelectedDate.equalsIgnoreCase(attendanceList.get(i).getAttDate()) && attendanceList.get(i).getPresent()) {
                            progressBar.setVisibility(View.GONE);
                            recy_view.setVisibility(View.VISIBLE);
                            tvNoData.setVisibility(View.GONE);

                            attendanceAdapter = new AttendanceAdapter(attendanceList.get(i).getInOutTime(), AttendanceActivity.this);
                            recy_view.setAdapter(attendanceAdapter);

                            break;
                        } else {

                            progressBar.setVisibility(View.GONE);
                            recy_view.setVisibility(View.GONE);
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText("No Data");

                        }
                    }
                }
            }
        });

        recy_view.setLayoutManager(new LinearLayoutManager(this));
        recy_view_summery.setLayoutManager(new LinearLayoutManager(this));
        tvNoData.setText("Click date to view in out");
        tv_title_summery.setText("Last three month summary");
        progressBar.setVisibility(View.GONE);
        recy_view.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);

        getEmpAttend();

        tv_month.setText("Month");
        tv_working_day.setText("Working Day");
        tv_absunt_days.setText("Absent Day");
        tv_total_days.setText("Total Day");

        tv2.setText("Absent");
        tv4.setText("Present");


        ivBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

    }


    private void setLisSpinner() {
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                widget.setCurrentDate(CalendarDay.from(spinnerHelper.get(position).getYear(), getCurrentSelectedMonth(spinnerHelper.get(position).getMonthName()) + 1, 1));
                widget.invalidateDecorators();
                tvNoData.setText("Click date to view in out");
                progressBar.setVisibility(View.GONE);
                recy_view.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);

                calculateMonthLeave(spinnerHelper.get(position).getYear(), spinnerHelper.get(position).getMonth() + 1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getEmpAttend() {
        getCallSociety().getEmpAttend("getEmpAttendNew", preferenceManager.getSocietyId(), "", empId, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<EmployeeAttendanceResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onNext(final EmployeeAttendanceResponse employeeAttendanceResponse) {

                        runOnUiThread(() -> {
                            new Gson().toJson(employeeAttendanceResponse);
                            attendanceList = employeeAttendanceResponse.getEmpAttend();

                            if (employeeAttendanceResponse.getEmpAttend() != null && employeeAttendanceResponse.getEmpAttend().size() > 0) {
                                for (int i = 0; i < employeeAttendanceResponse.getEmpAttend().size(); i++) {
                                    StringTokenizer st = new StringTokenizer(employeeAttendanceResponse.getEmpAttend().get(i).getAttDate(), "-");
                                    int year = Integer.parseInt(st.nextToken());
                                    int month = Integer.parseInt(st.nextToken());
                                    int day = Integer.parseInt(st.nextToken());
                                    if (employeeAttendanceResponse.getEmpAttend().get(i).getPresent()) {
                                        calendarDayHashSetPresent.add(CalendarDay.from(year, month, day));
                                    } else {
                                        calendarDayHashSetAbsent.add(CalendarDay.from(year, month, day));
                                    }
                                }


                                widget.addDecorators(new EventDecorator(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.bg_present), calendarDayHashSetPresent));
                                widget.addDecorators(new EventDecorator(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.bg_absent), calendarDayHashSetAbsent));


                                calculateMonthLeave(year, month+1);
                            }

                            if (employeeAttendanceResponse.getSummary() != null && employeeAttendanceResponse.getSummary().size() > 0) {

                                lin_summery.setVisibility(View.VISIBLE);
                                LastAttendanceViewAdapter lastAttendanceViewAdapter = new LastAttendanceViewAdapter(AttendanceActivity.this, employeeAttendanceResponse.getSummary());
                                recy_view_summery.setAdapter(lastAttendanceViewAdapter);

                            } else {

                                lin_summery.setVisibility(View.GONE);
                            }
                        });
                    }
                });
    }

    public static class EventDecorator implements DayViewDecorator {

        private final Drawable color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(Drawable color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(color);
            view.addSpan(new ForegroundColorSpan(Color.WHITE));
        }
    }

    private int getCurrentSelectedMonth(String cMonth) {
        for (int i = 0; i < getResources().getStringArray(R.array.month1).length; i++) {

            if (getResources().getStringArray(R.array.month1)[i].equalsIgnoreCase(cMonth)) {
                return i;
            }

        }

        return 0;
    }

    @SuppressLint("SetTextI18n")
    public void calculateMonthLeave(int yearC, int monthC) {

        if (attendanceList != null) {

            int abCount = 0;
            int pCount = 0;

            for (int i = 0; i < attendanceList.size(); i++) {
                StringTokenizer st = new StringTokenizer(attendanceList.get(i).getAttDate(), "-");
                int yearT = Integer.parseInt(st.nextToken());
                int monthT = Integer.parseInt(st.nextToken());
                if (yearC == yearT && monthC == monthT) {

                    if (attendanceList.get(i).getPresent()) {
                        pCount++;
                    } else {
                        abCount++;
                    }

                }
            }

            tv2.setText("Absent (" + abCount + ")");
            tv4.setText("Present (" + pCount + ")");


        }
    }



}