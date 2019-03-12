package com.example.android.scheduler;

import com.example.android.scheduler.global.Constants;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

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
    public void calendar_roll() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 11, 31);
        System.out.println(Constants.shortDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println(Constants.shortDateFormat.format(calendar.getTime()));
    }
}