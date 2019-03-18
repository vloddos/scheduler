package com.example.android.scheduler.models;

import com.example.android.scheduler.global.CalendarInterval;

import java.io.Serializable;

public class Event implements Serializable {

    private static final long serialVersionUID = -8023521667978959263L;

    private int id;

    public String name;
    public CalendarInterval interval;
    public String description;
    //owner
    //place
    //status
    //tag

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
}
