package com.example.android.scheduler.fragments;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.scheduler.R;
import com.example.android.scheduler.activities.MainActivity;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.global.Global;

import java.util.Optional;

public class DayFragment extends Fragment {

    public static final String title = "day";

    private static SparseArray<String> daysOfWeek = new SparseArray<>();

    static {
        daysOfWeek.put(Calendar.MONDAY, "пн");
        daysOfWeek.put(Calendar.TUESDAY, "вт");
        daysOfWeek.put(Calendar.WEDNESDAY, "ср");
        daysOfWeek.put(Calendar.THURSDAY, "чт");
        daysOfWeek.put(Calendar.FRIDAY, "пт");
        daysOfWeek.put(Calendar.SATURDAY, "сб");
        daysOfWeek.put(Calendar.SUNDAY, "вс");
    }

    private boolean viewCreated;

    public TextView dayOfWeek;
    public TextView dayOfMonth;

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
        setUserVisibleHint(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        dayOfWeek = view.findViewById(R.id.day_of_week);
        dayOfMonth = view.findViewById(R.id.day_of_month);

        viewCreated = true;

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && viewCreated) {
            Calendar calendar = Optional.ofNullable(Global.selectedCalendar).orElse(Calendar.getInstance());
            dayOfWeek.setText(daysOfWeek.get(calendar.get(Calendar.DAY_OF_WEEK)));
            dayOfMonth.setText(Constants.sdf.format(calendar.getTime()));
        }
    }

    public void day() {
        Optional.ofNullable(getActivity()).ifPresent(
                activity ->
                        Toast.makeText(
                                activity,
                                "day",
                                Toast.LENGTH_SHORT
                        ).show()
        );
    }
}
