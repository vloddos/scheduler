package com.example.android.scheduler;

import android.widget.TextView;

import com.example.android.scheduler.global.Constants;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void string_join_test() {
        assertEquals("", String.join(";", new ArrayList<>()));
    }

    @Test
    public void calendar_setTime() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
        calendar.setTime(Constants.shortDateFormat.parse("06.08.2005"));
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.SECOND, -1);
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
    }

    @Test
    public void calendar_set_hour() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
    }

    @Test
    public void substring() {
        System.out.println("1234abc".substring(0, 5));
    }

    @Test
    public void addHourOfDay() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Constants.fullDateFormat.parse("2019-07-09 23:00"));
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
        //calendar.add(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
    }

    @Test
    public void addDayOfMonth() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Constants.shortDateFormat.parse("31.12.2019"));
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
    }

    @Test
    public void addWeekOfYear() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Constants.shortDateFormat.parse("20.12.2019"));
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
    }

    @Test
    public void timezone() {
        for (String tz : TimeZone.getAvailableIDs())
            System.out.println(tz);
    }

    @Test
    public void calendarsWithDifferentTimeZones1() throws ParseException {
        Calendar a = Calendar.getInstance();
        a.setTimeInMillis(1562851754317L);
        Calendar b = (Calendar) a.clone();

        System.out.println(a.getTimeZone().getID());
        System.out.println(b.getTimeZone().getID());
        System.out.println(Constants.fullDateFormat.format(a.getTime()));
        System.out.println(Constants.fullDateFormat.format(b.getTime()));
        System.out.println(a.before(b));

//        a.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        Constants.fullDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        /*a.setTime(Constants.fullDateFormat.parse(Constants.fullDateFormat.format(a.getTime())));
        Constants.fullDateFormat.setTimeZone(TimeZone.getDefault());*/

        System.out.println(a.getTimeZone().getID());
        System.out.println(b.getTimeZone().getID());
        System.out.println(Constants.fullDateFormat.format(a.getTime()));
        System.out.println(Constants.fullDateFormat.format(b.getTime()));
        System.out.println(a.before(b));
    }

    @Test
    public void calendarsWithDifferentTimeZones2() {
        Calendar a = Calendar.getInstance(), b = (Calendar) a.clone(), c = (Calendar) a.clone();
        b.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        c.set(Calendar.HOUR_OF_DAY, 16);
        System.out.println(a.get(Calendar.HOUR_OF_DAY));
        System.out.println(b.get(Calendar.HOUR_OF_DAY));
        //System.out.println(a.equals(b));
        //System.out.println(c.before(b));

    }

    @Test
    public void instantsWithDifferentTimeZones() {
        Calendar a = Calendar.getInstance(), b = (Calendar) a.clone(), c = (Calendar) a.clone();
        System.out.println(a.toInstant().atZone(ZoneId.systemDefault()));
        System.out.println(b.toInstant().atZone(ZoneId.systemDefault()));
        a.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        System.out.println(a.toInstant().atZone(ZoneId.of(a.getTimeZone().getID())));
        System.out.println(b.toInstant().atZone(ZoneId.systemDefault()));
    }

    @Test
    public void geolocation() throws ParseException {
//        System.out.println(Constants.fullDateFormat.getTimeZone().getID());

        Calendar a = Calendar.getInstance();
        Calendar b = (Calendar) a.clone();

        System.out.println(a.getTimeZone().getID());
        System.out.println(b.getTimeZone().getID());
        System.out.println(Constants.fullDateFormat.format(a.getTime()));
        System.out.println(Constants.fullDateFormat.format(b.getTime()));
        System.out.println(a.before(b));

        Constants.fullDateFormat.setTimeZone(a.getTimeZone());
        a.setTime(Constants.fullDateFormat.parse(Constants.fullDateFormat.format(a.getTime())));
        Constants.fullDateFormat.setTimeZone(TimeZone.getDefault());

        System.out.println(a.getTimeZone().getID());
        System.out.println(b.getTimeZone().getID());
        System.out.println(Constants.fullDateFormat.format(a.getTime()));
        System.out.println(Constants.fullDateFormat.format(b.getTime()));
        System.out.println(a.before(b));
    }
}