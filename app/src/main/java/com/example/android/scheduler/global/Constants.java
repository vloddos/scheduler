package com.example.android.scheduler.global;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {

    // FIXME: 13.03.2019 threadlocal SimpleDateFormat?
    public static final SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));
}
