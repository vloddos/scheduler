package com.example.android.scheduler.client;

import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.models.Event;

import java.util.ArrayList;

public interface EventService {

    void add(Event event);

    ArrayList<Event> get(CalendarInterval interval);

    void update(Event event);

    void delete(int id);
}
