package com.example.android.scheduler;

import android.widget.TextView;

import com.example.android.scheduler.global.Constants;

import org.junit.Test;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

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
        calendar.setTime(Constants.shortDateFormat.parse("31.07.2019"));
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        System.out.println(Constants.fullDateFormat.format(calendar.getTime()));
    }
}