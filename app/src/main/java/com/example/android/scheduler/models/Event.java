package com.example.android.scheduler.models;

import com.example.android.scheduler.global.CalendarInterval;

import java.io.Serializable;

public class Event implements Serializable {
    // TODO: 18.03.2019 serialver 
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
