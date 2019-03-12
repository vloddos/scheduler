package com.example.android.scheduler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.scheduler.R;
import com.example.android.scheduler.activities.MainActivity;
import com.example.android.scheduler.global.Global;

import java.util.Calendar;

public class WeekFragment extends Fragment implements Selectable {

    private static final int[] daysOfWeek = new int[]{
            Calendar.MONDAY,
            Calendar.TUESDAY,
            Calendar.WEDNESDAY,
            Calendar.THURSDAY,
            Calendar.FRIDAY,
            Calendar.SATURDAY,
            Calendar.SUNDAY
    };

    public static final String title = "week";

    private TextView[] weekNumbers;

    public TextView mondayNumber;
    public TextView tuesdayNumber;
    public TextView wednesdayNumber;
    public TextView thursdayNumber;
    public TextView fridayNumber;
    public TextView saturdayNumber;
    public TextView sundayNumber;

    public WeekFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).weekFragment = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        weekNumbers = new TextView[]{
                mondayNumber = view.findViewById(R.id.mondayNumber),
                tuesdayNumber = view.findViewById(R.id.tuesdayNumber),
                wednesdayNumber = view.findViewById(R.id.wednesdayNumber),
                thursdayNumber = view.findViewById(R.id.thursdayNumber),
                fridayNumber = view.findViewById(R.id.fridayNumber),
                saturdayNumber = view.findViewById(R.id.saturdayNumber),
                sundayNumber = view.findViewById(R.id.sundayNumber)
        };

        return view;
    }

    @Override
    public void select() {
        Calendar calendar = Global.selectedCalendar;
        calendar = calendar == null ? Calendar.getInstance() : (Calendar) calendar.clone();
        //Log.i(MainActivity.LOG_TAG, "day=" + calendar.get(Calendar.DAY_OF_MONTH));

        for (int i = 0; i < weekNumbers.length; ++i) {
            calendar.set(Calendar.DAY_OF_WEEK, daysOfWeek[i]);
            weekNumbers[i].setText("" + calendar.get(Calendar.DAY_OF_MONTH));
        }
    }
}
