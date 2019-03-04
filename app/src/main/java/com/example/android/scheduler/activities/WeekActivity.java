package com.example.android.scheduler.activities;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.android.scheduler.R;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.global.Global;

import java.util.Date;

public class WeekActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void test(View view) {
        Calendar c = Global.selectedCalendar;
        Toast.makeText(
                this,
                Constants.sdf.format(c == null ? new Date() : c.getTime()),
                Toast.LENGTH_SHORT
        ).show();
    }
}
