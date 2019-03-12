package com.example.android.scheduler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.android.scheduler.R;
import com.example.android.scheduler.activities.MainActivity;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.global.Global;

import java.util.Calendar;
import java.util.Optional;

public class MonthFragment extends Fragment implements Selectable {

    public static final String title = "month";

    public CalendarView calendarView;

    public MonthFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).monthFragment = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_month, container, false);

        calendarView = v.findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(
                (view, year, month, dayOfMonth) -> {
                    Global.selectedCalendar = Calendar.getInstance();
                    //Global.selectedCalendar.clear();
                    Global.selectedCalendar.set(year, month, dayOfMonth);
                    Toast.makeText(
                            getActivity(),
                            Constants.fdf.format(Global.selectedCalendar.getTime()),
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        return v;
    }

    @Override
    public void select() {
        calendarView.setDate(
                Optional.ofNullable(Global.selectedCalendar)
                        .orElse(Calendar.getInstance())
                        .getTimeInMillis()
        );
    }
}