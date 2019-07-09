package com.example.android.scheduler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.scheduler.R;
import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.models.Event;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

// TODO: 18.03.2019 add datetime info there or in Event class
public class EventListActivity extends AppCompatActivity {

    private TextView eventListDateTime;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Intent intent = getIntent();

        Calendar calendar = (Calendar) intent.getSerializableExtra("calendar");
        ArrayList<Event> eventList = (ArrayList<Event>) intent.getSerializableExtra("eventList");

        eventListDateTime = findViewById(R.id.event_list_date_time);
        eventListDateTime.setText(Constants.eventListDateTimeFormat.format(calendar.getTime()));

        eventListView = findViewById(R.id.event_list_view);
        eventListView.setOnItemClickListener(
                (parent, view, position, id) -> {
                    Intent intent1 = new Intent(this, EventActivity.class);
                    //intent1.putExtra("add", false);
                    try {
                        intent1.putExtra("event", (Event) eventList.get(position).clone());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        return;
                    }
                    startActivity(intent1);
                }
        );

        ArrayAdapter<Event> eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        eventAdapter.addAll(eventList);

        eventListView.setAdapter(eventAdapter);
    }

    public void add(View view) throws ParseException {
        Intent intent1 = new Intent(this, EventActivity.class);
        intent1.putExtra("add", true);

        Calendar from = Calendar.getInstance();
        from.setTime(Constants.eventListDateTimeFormat.parse(eventListDateTime.getText().toString()));
        Calendar to = (Calendar) from.clone();
        //to.set(Calendar.MINUTE, 59);
        to.add(Calendar.HOUR_OF_DAY, 1);

        intent1.putExtra(
                "event",
                new Event(
                        new Random().nextInt(),
                        "new event",
                        new CalendarInterval(from, to),
                        "description"
                )
        );
        startActivity(intent1);
    }
}
