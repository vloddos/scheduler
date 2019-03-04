package com.example.android.scheduler;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private CalendarView calendarView;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendar_month);
        calendarView.setOnDateChangeListener(
                (view, year, month, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth);
                    Toast.makeText(
                            this,
                            sdf.format(calendar.getTime()),
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );
    }

    public void today(View view) {
        calendarView.setDate(new Date().getTime());
    }

    public void week(View view) {
        startActivity(new Intent(this, WeekActivity.class));
    }

    public void day(View view) {
        startActivity(new Intent(this, DayActivity.class));
    }
}
