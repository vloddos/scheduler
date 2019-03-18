package com.example.android.scheduler.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.scheduler.R;
import com.example.android.scheduler.models.Event;

// TODO: 18.03.2019 add date/time view
public class EventActivity extends AppCompatActivity {

    private TextView eventId;
    private EditText eventName;
    private EditText eventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventId = findViewById(R.id.event_id);
        eventName = findViewById(R.id.event_name);
        eventDescription = findViewById(R.id.event_description);

        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");

        eventId.setText("" + event.getId());
        eventName.setText(event.name);
        eventDescription.setText(event.description);
    }

    public void save(View view) {

    }
}
