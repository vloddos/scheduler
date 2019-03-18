package com.example.android.scheduler.models;

import com.example.android.scheduler.global.CalendarInterval;

import java.io.Serializable;

// TODO: 18.03.2019 remove seconds
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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Event(id, name, (CalendarInterval) interval.clone(), description);
    }
}
