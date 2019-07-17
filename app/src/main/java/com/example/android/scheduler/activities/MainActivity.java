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

// TODO: 18.03.2019 activities connection
// TODO: 18.03.2019 remake week/day view
// FIXME: 18.03.2019 Calendar.MONTH 0/1?
public class MainActivity extends AppCompatActivity {

    public static final int ADD_EVENT_REQUEST = 0;
    public static final int REMOVE_EVENT_REQUEST = 1;
    public static final int UPDATE_EVENT_REQUEST = 2;

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

    public void today(View view) {
        Global.selectedCalendar = Calendar.getInstance();
        onPageChangeListener.onPageSelected(viewPager.getCurrentItem());
    }

    //возможно понадобится в будущем
    public void sync(View view) {
        //кнопка должна крутиться пока идет синхронизация
        /*(
                (EventManageable) new Fragment[]{
                        this.monthFragment, this.weekFragment, this.dayFragment
                }[viewPager.getCurrentItem()]
        ).setEvents();*/
//        onPageChangeListener.onPageSelected(viewPager.getCurrentItem());
        Constants.syncTimeZone();
        recreate();//все активити пересоздается целиком или viewpager надо очищать?
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
        startActivityForResult(intent, ADD_EVENT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.i(LOG_TAG, "onActivityResult from MainActivity was called, request code:" + requestCode);//65538
            switch (requestCode) {
                case ADD_EVENT_REQUEST:
                    (
                            (EventManageable) new Fragment[]{
                                    monthFragment, weekFragment, dayFragment
                            }[viewPager.getCurrentItem()]
                    ).addEvent((Event) data.getSerializableExtra("event"));
                    break;
                case REMOVE_EVENT_REQUEST:
                    (
                            (EventManageable) new Fragment[]{
                                    monthFragment, weekFragment, dayFragment
                            }[viewPager.getCurrentItem()]
                    ).removeEvent(((Event) data.getSerializableExtra("event")).getId());
                    break;
                case UPDATE_EVENT_REQUEST:
                    (
                            (EventManageable) new Fragment[]{
                                    monthFragment, weekFragment, dayFragment
                            }[viewPager.getCurrentItem()]
                    ).updateEvent((Event) data.getSerializableExtra("event"));
                    break;
            }
        }
    }
}