package com.example.android.scheduler.activities;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.android.scheduler.R;
import com.example.android.scheduler.fragments.MainFragmentPagerAdapter;

import java.util.Date;

// TODO: 05.03.2019 month/week/day view in this activity
public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private CalendarView calendarView;
    private Calendar selectedCalendar;

    //@RequiresApi(api = Build.VERSION_CODES.N)
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), this));

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        /*findViewById(R.id.days).setOnTouchListener((v, event) -> {
            //Log.i(LOG_TAG, event.getAction());
            //int action = MotionEventCompat.getActionMasked(event);

            switch (event.getAction()) {
                case (MotionEvent.ACTION_DOWN):
                    Log.i(LOG_TAG, "Action was DOWN");
                    return true;
                case (MotionEvent.ACTION_MOVE):
                    Log.i(LOG_TAG, "Action was MOVE");
                    return true;
                case (MotionEvent.ACTION_UP):
                    Log.i(LOG_TAG, "Action was UP");
                    return true;
                case (MotionEvent.ACTION_CANCEL):
                    Log.i(LOG_TAG, "Action was CANCEL");
                    return true;
                case (MotionEvent.ACTION_OUTSIDE):
                    Log.i(LOG_TAG, "Movement occurred outside bounds " +
                            "of current screen element");
                    return true;
            }

            Log.i(LOG_TAG, "" + event.getAction());
            return false;
        });*/
//
//        calendarView = findViewById(R.id.calendarView);
//        calendarView.setOnDateChangeListener(
//                (view, year, month, dayOfMonth) -> {
//                    selectedCalendar = Calendar.getInstance();
//                    selectedCalendar.set(year, month, dayOfMonth);
//                    Toast.makeText(
//                            this,
//                            Constants.sdf.format(selectedCalendar.getTime()),
//                            Toast.LENGTH_SHORT
//                    ).show();
//                }
//        );
    }

    public void today(View view) {
        selectedCalendar = null;
        calendarView.setDate(new Date().getTime());
//        Toast.makeText(
//                this,
//                //((ConstraintLayout) view.getParent()).get
//                Toast.LENGTH_SHORT
//        ).show();
    }

    public void test(View view) {
        Toast.makeText(
                this,
                "" + ((TableRow) view.getParent()).indexOfChild(view),
                Toast.LENGTH_SHORT
        ).show();
    }

//    public void week(View view) {
//        startActivity(new Intent(this, WeekActivity.class));
//    }
//
//    public void day(View view) {
//        startActivity(new Intent(this, DayActivity.class));
//    }
}
