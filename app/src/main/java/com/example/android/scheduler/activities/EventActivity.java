package com.example.android.scheduler.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

// TODO: 18.03.2019 fix description view size
public class EventActivity extends AppCompatActivity {

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
        for (int i = 0; i < timeZones.length; ++i)
            if (timeZones[i].equals(id)) {
                spinner.setSelection(i);
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
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        Constants.dateTimeFormat.setTimeZone(TimeZone.getTimeZone(spinner.getSelectedItem().toString()));
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
            event.interval = new CalendarInterval(from, to);//убрать баг с неправильным интервалом
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            new CalendarIntervalDialogFragment().show(getSupportFragmentManager(), "");
            return;
        } finally {
            Constants.dateTimeFormat.setTimeZone(TimeZone.getDefault());
        }

        event.name = eventName.getText().toString();
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

        Intent intent = new Intent();
        intent.putExtra("id", event.getId());
        setResult(RESULT_OK, intent);
        finish();
    }

    public static class CalendarIntervalDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Invalid calendar interval\nstart datetime>end datetime")
                            .setNeutralButton("ok", (dialog, id) -> {
                            })
                            .create();
        }
    }
}
