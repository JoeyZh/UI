package com.joey.ui.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.joey.ui.base.BaseActivity;
import com.joey.utils.TimeUtils;
import com.joey.utils.ToastUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

/**
 * Created by Joey on 2017/5/16.
 */

public class CalenderActivity extends BaseActivity {

    MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calender);
        setTitle("日历选择");
        calendarView = (MaterialCalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                ToastUtil.show(getApplicationContext(), TimeUtils.convertDateToStr(date.getDate(),TimeUtils.FORMATTER_DATE));
            }
        });
    }
}
