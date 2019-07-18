package com.example.android.scheduler.models;

import com.example.android.scheduler.global.CalendarInterval;

import java.io.Serializable;
import java.util.TimeZone;

public class Event implements Serializable, Cloneable {

    private static final long serialVersionUID = -8023521667978959263L;

    private int id;

    public String name;
    public CalendarInterval interval;
    public String description;

    //owner
    //place
    //status
    //tag

    public Event() {
    }

    public Event(String name, CalendarInterval interval, String description) {
        this.name = name;
        this.interval = interval;
        this.description = description;
    }

    public Event(int id, String name, CalendarInterval interval, String description) {
        this.id = id;
        this.name = name;
        this.interval = interval;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setTimeZone(TimeZone timeZone) {
        interval.setFromTimeZone(timeZone);
        interval.setToTimeZone(timeZone);
    }

    public TimeZone getTimeZone() {
        return interval.getFromTimeZone();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object clone() {
        try {
            Event event = (Event) super.clone();
            event.id = id;
            event.name = name;
            event.interval = (CalendarInterval) interval.clone();
            event.description = description;
            return event;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
}