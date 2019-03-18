package com.example.android.scheduler;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.android.scheduler.global.Constants;

import java.text.ParseException;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView textView;

    public static DatePickerFragment newInstance(TextView textView) {
        DatePickerFragment f = new DatePickerFragment();
        f.textView = textView;//args.putSerializable?
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Constants.dateFormat.parse(textView.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return
                new DatePickerDialog(
                        getActivity(),
                        this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        textView.setText(Constants.dateFormat.format(calendar.getTime()));
    }
}
