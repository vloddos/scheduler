package com.example.android.scheduler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.scheduler.R;
import com.example.android.scheduler.models.Event;

import java.util.ArrayList;

// TODO: 18.03.2019 add datetime info there or in Event class
public class EventListActivity extends AppCompatActivity {

    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Intent intent = getIntent();
        ArrayList<Event> eventList = (ArrayList<Event>) intent.getSerializableExtra("eventList");

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

    public void add(View view) {
        Intent intent1 = new Intent(this, EventActivity.class);
        intent1.putExtra("add", true);
        intent1.putExtra("event", new Event());//new Event("new event", new Event(), "description")
        startActivity(intent1);
    }
}
