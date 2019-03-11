package com.example.android.scheduler.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.android.scheduler.activities.MainActivity;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] titles = {MonthFragment.title, WeekFragment.title, DayFragment.title};
    private Context context;

    public MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Log.i(MainActivity.LOG_TAG, "get item i=" + i);

        switch (i) {
            case 0:
                return new MonthFragment();
            case 1:
                return new WeekFragment();
            case 2:
                return new DayFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
