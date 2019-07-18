package com.example.android.scheduler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.android.scheduler.R;
import com.example.android.scheduler.client.StubEventManager;
import com.example.android.scheduler.dialogs.SignOutDialogFragment;
import com.example.android.scheduler.fragments.DayFragment;
import com.example.android.scheduler.fragments.EventManageable;
import com.example.android.scheduler.fragments.MonthFragment;
import com.example.android.scheduler.fragments.Selectable;
import com.example.android.scheduler.fragments.WeekFragment;
import com.example.android.scheduler.fragments.adapters.MainFragmentPagerAdapter;
import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.global.Global;
import com.example.android.scheduler.models.Event;

import java.util.Calendar;
import java.util.Random;

// FIXME: 18.03.2019 Calendar.MONTH 0/1?
public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
            MainActivity self = MainActivity.this;
            Fragment f = new Fragment[]{self.monthFragment, self.weekFragment, self.dayFragment}[i];
            ((Selectable) f).select();
            ((EventManageable) f).setEvents(//todo refactor less casts
                    StubEventManager.getInstance().get(
                            ((EventManageable) f).getVisibleInterval()
                    )
            );
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

        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        SignOutDialogFragment dialog = new SignOutDialogFragment();
        dialog.setPositiveListener(
                (dialog1, id) -> {
                    setResult(RESULT_OK);
                    super.onBackPressed();
                }
        );
        dialog.show(getSupportFragmentManager(), "");
    }

    public void today(View view) {
        Global.selectedCalendar = Calendar.getInstance();
        onPageChangeListener.onPageSelected(viewPager.getCurrentItem());
    }

    public void sync(View view) {

    }

    public void addEvent(View view) {
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("add", true);

        Calendar
                now = Calendar.getInstance(),
                from = (Calendar) Global.selectedCalendar.clone();

        from.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
        from.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
        from.set(Calendar.SECOND, 0);
        from.set(Calendar.MILLISECOND, 0);

        Calendar to = (Calendar) from.clone();
        to.add(Calendar.HOUR_OF_DAY, 1);

        intent.putExtra(
                "event",
                new Event(
                        new Random().nextInt(),
                        "new event",
                        new CalendarInterval(from, to),
                        "description"
                )
        );
        startActivityForResult(intent, RequestCodes.ADD_EVENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RequestCodes.ADD_EVENT && data != null) {
                (
                        (EventManageable) new Fragment[]{
                                monthFragment, weekFragment, dayFragment
                        }[viewPager.getCurrentItem()]
                ).addEvent((Event) data.getSerializableExtra("event"));
            }
        }
    }
}