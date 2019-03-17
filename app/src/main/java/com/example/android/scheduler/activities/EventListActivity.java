package com.example.android.scheduler.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.scheduler.R;
import com.example.android.scheduler.client.StubEventManager;
import com.example.android.scheduler.models.Event;

public class EventListActivity extends AppCompatActivity {

    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        eventListView = findViewById(R.id.event_list_view);
        eventListView.setOnItemClickListener(
                (parent, view, position, id) -> {

                }
        );
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        eventListView.setAdapter(eventAdapter);
    }
}
