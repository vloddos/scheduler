package com.example.android.scheduler.global;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Constants {

    // TODO: 18.03.2019 для каждого класса запилить свои форматы а не брать глобальные?
    public static final SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm zzzz");
    public static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat eventListDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm-HH:59");

    public static void syncTimeZone() {
        fullDateFormat.setTimeZone(TimeZone.getDefault());
        shortDateFormat.setTimeZone(TimeZone.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        timeFormat.setTimeZone(TimeZone.getDefault());
        dateTimeFormat.setTimeZone(TimeZone.getDefault());
        eventListDateTimeFormat.setTimeZone(TimeZone.getDefault());
    }
}
