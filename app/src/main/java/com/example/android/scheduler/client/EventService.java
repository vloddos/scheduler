package com.example.android.scheduler.client;

import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.models.Event;

import java.util.List;

public interface EventService {

    void add(Event event) throws Exception;

    List<Event> get(CalendarInterval interval) throws Exception;

    void update(Event event) throws Exception;

    void delete(int id) throws Exception;
}
