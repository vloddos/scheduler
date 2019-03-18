package com.example.android.scheduler.activities;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.scheduler.DatePickerFragment;
import com.example.android.scheduler.R;
import com.example.android.scheduler.TimePickerFragment;
import com.example.android.scheduler.client.StubEventManager;
import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.models.Event;

import java.text.ParseException;
import java.util.Calendar;

// TODO: 18.03.2019 add date/time view
public class EventActivity extends AppCompatActivity {

    /**
     * must use only for {@link EventActivity#save(View)}
     * to define which method of {@link com.example.android.scheduler.client.EventService}
     * should call
     */
    private boolean add;

    private Event event;

    private TextView eventId;
    private EditText eventName;
    private TextView startDate;
    private TextView startTime;
    private TextView endDate;
    private TextView endTime;
    private EditText eventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventId = findViewById(R.id.event_id);
        eventName = findViewById(R.id.event_name);
        startDate = findViewById(R.id.start_date);
        startTime = findViewById(R.id.start_time);
        endDate = findViewById(R.id.end_date);
        endTime = findViewById(R.id.end_time);
        eventDescription = findViewById(R.id.event_description);

        Intent intent = getIntent();
        add = intent.getBooleanExtra("add", false);
        event = (Event) intent.getSerializableExtra("event");

        eventId.setText("" + event.getId());
        eventName.setText(event.name);

        startDate.setText(
                Constants.dateFormat.format(event.interval.getFrom().getTime())
        );
        startTime.setText(
                Constants.timeFormat.format(event.interval.getFrom().getTime())
        );
        endDate.setText(
                Constants.dateFormat.format(event.interval.getTo().getTime())
        );
        endTime.setText(
                Constants.timeFormat.format(event.interval.getTo().getTime())
        );

        eventDescription.setText(event.description);
    }

    public void showTimePickerDialog(View v) throws ParseException {
        DialogFragment newFragment = TimePickerFragment.newInstance((TextView) v);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = DatePickerFragment.newInstance((TextView) v);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void save(View view) {
        event.name = eventName.getText().toString();

        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        try {
            from.setTime(
                    Constants.dateTimeFormat.parse(
                            startDate.getText().toString() +
                                    " " +
                                    startTime.getText().toString()
                    )
            );

            to.setTime(
                    Constants.dateTimeFormat.parse(
                            endDate.getText().toString() +
                                    " " +
                                    endTime.getText().toString()
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        event.interval = new CalendarInterval(from, to);

        event.description = eventDescription.getText().toString();

        if (add) {
            StubEventManager.getInstance().add(event);
        } else {
            StubEventManager.getInstance().update(event);
        }
    }
}
