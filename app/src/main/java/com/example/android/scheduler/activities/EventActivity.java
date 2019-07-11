package com.example.android.scheduler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.scheduler.DatePickerFragment;
import com.example.android.scheduler.R;
import com.example.android.scheduler.TimePickerFragment;
import com.example.android.scheduler.client.StubEventManager;
import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.models.Event;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.stream.IntStream;

// TODO: 18.03.2019 fix description view size
public class EventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String LOG_TAG = EventActivity.class.getSimpleName();
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
    private Button removeButton;
    private Spinner spinner;

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
        removeButton = findViewById(R.id.removeButton);
        spinner = findViewById(R.id.spinner);

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

        if (add)
            removeButton.setEnabled(false);

        String id = event.getTimeZone().getID();
        String[] timeZones = TimeZone.getAvailableIDs();
        spinner.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        timeZones
                )
        );
        spinner.setOnItemSelectedListener(this);
        for (int i = 0; i < timeZones.length; ++i)
            if (timeZones[i].equals(id)) {
                spinner.setSelection(i);//должно зависеть от текущего часового пояса?
                break;
            }
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
        event.interval = new CalendarInterval(from, to);//убрать баг с неправильным интервалом
        event.setTimeZone(TimeZone.getTimeZone(spinner.getSelectedItem().toString()));

        event.description = eventDescription.getText().toString();

        if (add) {
            StubEventManager.getInstance().add(event);
        } else {
            StubEventManager.getInstance().update(event);
        }

        Intent intent = new Intent();
        intent.putExtra("event", event);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void remove(View view) {
        StubEventManager.getInstance().delete(event.getId());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //event.setTimeZone(TimeZone.getTimeZone(((TextView) view).getText().toString()));
        //Log.i(LOG_TAG, spinner.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
