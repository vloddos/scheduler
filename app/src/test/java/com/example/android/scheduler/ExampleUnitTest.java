package com.example.android.scheduler;

import com.example.android.scheduler.global.Constants;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    public void locale() {
        System.out.println(
                Arrays.toString(
                        Constants.shortDateFormat.format(new Date()).split(" ")
                )
        );
    }
}