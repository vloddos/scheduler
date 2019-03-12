package com.example.android.scheduler.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
    }

    public void today(View view) {
        Global.selectedCalendar = null;
        onPageChangeListener.onPageSelected(viewPager.getCurrentItem());
    }

    public void test(View view) {

    }
}