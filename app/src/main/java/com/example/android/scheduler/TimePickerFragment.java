package com.example.android.scheduler;

import android.app.Dialog;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.scheduler.activities.EventActivity;
import com.example.android.scheduler.global.Constants;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private int id;
    private int hour;
    private int minute;

    public static TimePickerFragment newInstance(int id, int hour, int minute) {
        TimePickerFragment f = new TimePickerFragment();

        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putInt("hour", hour);
        args.putInt("minute", minute);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        if (getArguments() != null) {
            id = getArguments().getInt("id");
            hour = getArguments().getInt("hour");
            minute = getArguments().getInt("minute");
        }

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(
                getActivity(),
                this,
                hour,
                minute,
                DateFormat.is24HourFormat(getActivity())
        );
    }

    /*@Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hour", hour);
        outState.putInt("minute", minute);
    }*/

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        ((TextView) getActivity().findViewById(id)).setText(
                Constants.timeFormat.format(calendar.getTime())
        );
    }
}
