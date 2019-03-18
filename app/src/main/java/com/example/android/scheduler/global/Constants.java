package com.example.android.scheduler.global;

import java.text.SimpleDateFormat;

public class Constants {

    // FIXME: 13.03.2019 threadlocal SimpleDateFormat?
    public static final SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
}
