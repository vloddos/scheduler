package com.example.android.scheduler.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.android.scheduler.R;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.global.Global;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private CalendarView calendarView;
    //private Calendar selectedCalendar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendar_month);
        calendarView.setOnDateChangeListener(
                (view, year, month, dayOfMonth) -> {
                    Global.selectedCalendar = Calendar.getInstance();
                    Global.selectedCalendar.set(year, month, dayOfMonth);
                    Toast.makeText(
                            this,
                            Constants.sdf.format(Global.selectedCalendar.getTime()),
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );
    }

    public void today(View view) {
        Global.selectedCalendar = null;
        calendarView.setDate(new Date().getTime());
//        Toast.makeText(
//                this,
//                //((ConstraintLayout) view.getParent()).get
//                Toast.LENGTH_SHORT
//        ).show();
    }

    public void week(View view) {
        startActivity(new Intent(this, WeekActivity.class));
    }

    public void day(View view) {
        startActivity(new Intent(this, DayActivity.class));
    }
}
