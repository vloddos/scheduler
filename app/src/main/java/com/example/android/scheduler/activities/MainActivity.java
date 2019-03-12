package com.example.android.scheduler.activities;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.example.android.scheduler.R;
import com.example.android.scheduler.fragments.DayFragment;
import com.example.android.scheduler.fragments.MainFragmentPagerAdapter;
import com.example.android.scheduler.fragments.MonthFragment;
import com.example.android.scheduler.fragments.Selectable;
import com.example.android.scheduler.fragments.WeekFragment;
import com.example.android.scheduler.global.Global;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            MainActivity self = MainActivity.this;
            new Selectable[]{self.monthFragment, self.weekFragment, self.dayFragment}[i].select();
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public Calendar selectedCalendar;
    private SparseArray<String> daysOfWeek = new SparseArray<>();

    {
        daysOfWeek.put(Calendar.MONDAY, "пн");
        daysOfWeek.put(Calendar.TUESDAY, "вт");
        daysOfWeek.put(Calendar.WEDNESDAY, "ср");
        daysOfWeek.put(Calendar.THURSDAY, "чт");
        daysOfWeek.put(Calendar.FRIDAY, "пт");
        daysOfWeek.put(Calendar.SATURDAY, "сб");
        daysOfWeek.put(Calendar.SUNDAY, "вс");
    }

    public MonthFragment monthFragment;
    public WeekFragment weekFragment;
    public DayFragment dayFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), this));
        viewPager.addOnPageChangeListener(onPageChangeListener);

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
//                (view, year, week, dayOfMonth) -> {
//                    selectedCalendar = Calendar.getInstance();
//                    selectedCalendar.set(year, week, dayOfMonth);
//                    Toast.makeText(
//                            this,
//                            Constants.fdf.format(selectedCalendar.getTime()),
//                            Toast.LENGTH_SHORT
//                    ).show();
//                }
//        );
    }

    public void today(View view) {
        Global.selectedCalendar = null;
        onPageChangeListener.onPageSelected(viewPager.getCurrentItem());
    }

    public void test(View view) {

    }

    public void getWeek(Calendar calendar) {
        calendar.get(Calendar.WEEK_OF_YEAR);
    }
}

/*
@RequiresApi(api = Build.VERSION_CODES.N)
    public void today(View view) {
        Global.selectedCalendar = null;
        Calendar now = Calendar.getInstance();

        int i = viewPager.getCurrentItem();
        Log.i(LOG_TAG, "" + i);
        switch (i) {
            case 0:
                ((CalendarView) findViewById(R.id.fragment_month)).setDate(now.getTimeInMillis());
                break;
            case 1:
                Log.i(LOG_TAG, "" + (findViewById(R.id.mon) != null));
                Log.i(LOG_TAG, "" + (findViewById(R.id.tue) != null));
                Log.i(LOG_TAG, "" + (findViewById(R.id.wed) != null));
                Log.i(LOG_TAG, "" + (findViewById(R.id.thu) != null));
                Log.i(LOG_TAG, "" + (findViewById(R.id.fri) != null));
                Log.i(LOG_TAG, "" + (findViewById(R.id.sat) != null));
                Log.i(LOG_TAG, "" + (findViewById(R.id.sun) != null));
                break;
            case 2:
                switch (now.get(Calendar.DAY_OF_WEEK)) {
                    case Calendar.MONDAY:
                        ((TextView) findViewById(R.id.day_of_week)).setText("пн");
                        break;
                    case Calendar.TUESDAY:
                        ((TextView) findViewById(R.id.day_of_week)).setText("вт");
                        break;
                    case Calendar.WEDNESDAY:
                        ((TextView) findViewById(R.id.day_of_week)).setText("ср");
                        break;
                    case Calendar.THURSDAY:
                        ((TextView) findViewById(R.id.day_of_week)).setText("чт");
                        break;
                    case Calendar.FRIDAY:
                        ((TextView) findViewById(R.id.day_of_week)).setText("пт");
                        break;
                    case Calendar.SATURDAY:
                        ((TextView) findViewById(R.id.day_of_week)).setText("сб");
                        break;
                    case Calendar.SUNDAY:
                        ((TextView) findViewById(R.id.day_of_week)).setText("вс");
                        break;
                }
                ((TextView) findViewById(R.id.day_of_month)).setText(Constants.sdf.format(now.getTime()));
                break;
        }
    }
 */