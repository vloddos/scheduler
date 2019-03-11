package com.example.android.scheduler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.scheduler.R;
import com.example.android.scheduler.activities.MainActivity;

import java.util.Optional;

public class WeekFragment extends Fragment {

    public static final String title = "week";

    private boolean viewCreated;

    public TextView mon;
    public TextView tue;
    public TextView wed;
    public TextView thu;
    public TextView fri;
    public TextView sat;
    public TextView sun;

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
        setUserVisibleHint(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        mon = view.findViewById(R.id.mon);
        tue = view.findViewById(R.id.tue);
        wed = view.findViewById(R.id.wed);
        thu = view.findViewById(R.id.thu);
        fri = view.findViewById(R.id.fri);
        sat = view.findViewById(R.id.sat);
        sun = view.findViewById(R.id.sun);

        viewCreated = true;

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && viewCreated) ;

    }

    public void week() {
        Optional.ofNullable(getActivity()).ifPresent(
                activity ->
                        Toast.makeText(
                                activity,
                                "week",
                                Toast.LENGTH_SHORT
                        ).show()
        );
    }
}
