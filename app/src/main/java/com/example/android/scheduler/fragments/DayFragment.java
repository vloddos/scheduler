package com.example.android.scheduler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.scheduler.R;
import com.example.android.scheduler.activities.MainActivity;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.global.Global;

import java.util.Calendar;
import java.util.Optional;

public class DayFragment extends Fragment implements Selectable {

    public static final String title = "day";

    private static final SparseArray<String> daysOfWeek = new SparseArray<>();

    static {
        daysOfWeek.put(Calendar.MONDAY, "пн");
        daysOfWeek.put(Calendar.TUESDAY, "вт");
        daysOfWeek.put(Calendar.WEDNESDAY, "ср");
        daysOfWeek.put(Calendar.THURSDAY, "чт");
        daysOfWeek.put(Calendar.FRIDAY, "пт");
        daysOfWeek.put(Calendar.SATURDAY, "сб");
        daysOfWeek.put(Calendar.SUNDAY, "вс");
    }

    public TextView dayOfWeek;
    public TextView dayOfMonth;

    public Button leftButton;
    public Button rightButton;

    public DayFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).dayFragment = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        dayOfWeek = view.findViewById(R.id.day_of_week);
        dayOfMonth = view.findViewById(R.id.day_of_month);

        leftButton = view.findViewById(R.id.leftButton);
        leftButton.setOnClickListener(this::change);

        rightButton = view.findViewById(R.id.rightButton);
        rightButton.setOnClickListener(this::change);

        return view;
    }

    @Override
    public void select() {
        Calendar calendar = Optional.ofNullable(Global.selectedCalendar).orElse(Calendar.getInstance());
        dayOfWeek.setText(daysOfWeek.get(calendar.get(Calendar.DAY_OF_WEEK)));
        dayOfMonth.setText(Constants.shortDateFormat.format(calendar.getTime()));
    }

    public void change(View view) {
        if (Global.selectedCalendar == null)
            Global.selectedCalendar = Calendar.getInstance();

        Global.selectedCalendar.add(Calendar.DAY_OF_MONTH, view.getId() == leftButton.getId() ? -1 : 1);

        select();
    }
}
