package com.example.android.scheduler.fragments;

import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.models.Event;

import java.util.List;

public interface EventManageable {

    void setEvents(List<Event> eventList);

    void addEvent(Event event);

    void removeEvent(int id);

    void updateEvent(Event event);

    CalendarInterval getVisibleInterval();
}
