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
import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.global.Global;
import com.example.android.scheduler.models.Event;

import java.util.Calendar;
import java.util.List;

public class WeekFragment extends Fragment implements Selectable, EventManageable {

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

    private TextView[] weekDays;
    public TextView mondayDay;
    public TextView tuesdayDay;
    public TextView wednesdayDay;
    public TextView thursdayDay;
    public TextView fridayDay;
    public TextView saturdayDay;
    public TextView sundayDay;

    private TextView[] weekMonths;
    public TextView mondayMonth;
    public TextView tuesdayMonth;
    public TextView wednesdayMonth;
    public TextView thursdayMonth;
    public TextView fridayMonth;
    public TextView saturdayMonth;
    public TextView sundayMonth;

    private TextView[] weekYears;
    public TextView mondayYear;
    public TextView tuesdayYear;
    public TextView wednesdayYear;
    public TextView thursdayYear;
    public TextView fridayYear;
    public TextView saturdayYear;
    public TextView sundayYear;

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

        weekDays = new TextView[]{
                mondayDay = view.findViewById(R.id.mondayDay),
                tuesdayDay = view.findViewById(R.id.tuesdayDay),
                wednesdayDay = view.findViewById(R.id.wednesdayDay),
                thursdayDay = view.findViewById(R.id.thursdayDay),
                fridayDay = view.findViewById(R.id.fridayDay),
                saturdayDay = view.findViewById(R.id.saturdayDay),
                sundayDay = view.findViewById(R.id.sundayDay)
        };

        weekMonths = new TextView[]{
                mondayMonth = view.findViewById(R.id.mondayMonth),
                tuesdayMonth = view.findViewById(R.id.tuesdayMonth),
                wednesdayMonth = view.findViewById(R.id.wednesdayMonth),
                thursdayMonth = view.findViewById(R.id.thursdayMonth),
                fridayMonth = view.findViewById(R.id.fridayMonth),
                saturdayMonth = view.findViewById(R.id.saturdayMonth),
                sundayMonth = view.findViewById(R.id.sundayMonth)
        };

        weekYears = new TextView[]{
                mondayYear = view.findViewById(R.id.mondayYear),
                tuesdayYear = view.findViewById(R.id.tuesdayYear),
                wednesdayYear = view.findViewById(R.id.wednesdayYear),
                thursdayYear = view.findViewById(R.id.thursdayYear),
                fridayYear = view.findViewById(R.id.fridayYear),
                saturdayYear = view.findViewById(R.id.saturdayYear),
                sundayYear = view.findViewById(R.id.sundayYear)
        };

        return view;
    }

    @Override
    public void select() {
        Calendar calendar = Global.selectedCalendar;
        calendar = calendar == null ? Calendar.getInstance() : (Calendar) calendar.clone();

        for (int i = 0; i < weekDays.length; ++i) {
            calendar.set(Calendar.DAY_OF_WEEK, daysOfWeek[i]);
            String[] s = Constants.shortDateFormat.format(calendar.getTime()).split("\\.");
            weekDays[i].setText(s[0]);
            weekMonths[i].setText(s[1]);
            weekYears[i].setText(s[2]);
        }
    }

    @Override
    public void setEvents(List<Event> eventList) {

    }

    @Override
    public void addEvent(Event event) {

    }

    @Override
    public void removeEvent(int id) {

    }

    @Override
    public void updateEvent(Event event) {

    }

    @Override
    public CalendarInterval getVisibleInterval() {
        return new CalendarInterval(Calendar.getInstance(), Calendar.getInstance());//stub
    }
    // TODO: 13.03.2019 scroll view???
}
