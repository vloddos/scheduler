package com.example.android.scheduler;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.android.scheduler.global.Constants;

import java.text.ParseException;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TextView textView;

    public static TimePickerFragment newInstance(TextView textView) {
        TimePickerFragment f = new TimePickerFragment();
        f.textView = textView;//args.putSerializable?
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Constants.timeFormat.parse(textView.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return
                new TimePickerDialog(
                        getActivity(),
                        this,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
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

        textView.setText(Constants.timeFormat.format(calendar.getTime()));
    }
}
