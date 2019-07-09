package com.example.android.scheduler.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.example.android.scheduler.R;
import com.example.android.scheduler.activities.MainActivity;
import com.example.android.scheduler.client.StubEventManager;
import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.global.Global;
import com.example.android.scheduler.models.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class MonthFragment extends Fragment implements Selectable, EventSettable {

    public static final String LOG_TAG = MonthFragment.class.getSimpleName();
    public static final String title = "month";

    public CalendarView calendarView;

    public MonthFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).monthFragment = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_month, container, false);

        calendarView = v.findViewById(R.id.calendar_view);
        /**default*/
        /*calendarView.setOnDateChangeListener(
                (view, year, month, dayOfMonth) -> {
                    Global.selectedCalendar = Calendar.getInstance();
                    Global.selectedCalendar.set(year, month, dayOfMonth);
                    Toast.makeText(
                            getActivity(),
                            Constants.fullDateFormat.format(Global.selectedCalendar.getTime()),
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );*/
        /**applandeo*/
        //чтобы при перелистывании на месяц вперед/назад отображались события этого месяца
        calendarView.setOnPreviousPageChangeListener(
                () -> {
                    setEvents();
                }
        );
        calendarView.setOnForwardPageChangeListener(
                () -> {
                    setEvents();
                }
        );
        calendarView.setOnDayClickListener(
                eventDay -> {
                    Global.selectedCalendar = eventDay.getCalendar();
                    select();
                    Toast.makeText(
                            getActivity(),
                            //Constants.fullDateFormat.format(Global.selectedCalendar.getTime()),
                            Constants.fullDateFormat.format(calendarView.getCurrentPageDate().getTime()),
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );
        setEvents();//костыль?

        return v;
    }

    @Override
    public void select() {
        /**default*/
        /*calendarView.setDate(
                Optional.ofNullable(Global.selectedCalendar)
                        .orElse(Calendar.getInstance())
                        .getTimeInMillis()
        );*/
        /**applandeo*/
        try {
            calendarView.setDate(
                    Optional.ofNullable(Global.selectedCalendar)
                            .orElse(Calendar.getInstance())
            );
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }
    }

    private int[] eventDayIcons = {
            R.drawable.numeric_9_plus_box_outline,
            R.drawable.numeric_1_box_outline, R.drawable.numeric_2_box_outline,
            R.drawable.numeric_3_box_outline, R.drawable.numeric_4_box_outline,
            R.drawable.numeric_5_box_outline, R.drawable.numeric_6_box_outline,
            R.drawable.numeric_7_box_outline, R.drawable.numeric_8_box_outline,
            R.drawable.numeric_9_box_outline
    };

    @Override
    public void setEvents() {
        Calendar from = (Calendar) calendarView.getCurrentPageDate().clone();//clone redundant
        Calendar to = (Calendar) from.clone();
        to.add(Calendar.MONTH, 1);
        Calendar stop = (Calendar) to.clone();
        List<Event> monthEventList;//добавить возможность перейти в eventlistactivity?
        List<EventDay> eventDayList = new ArrayList<>();

        try {
            monthEventList = StubEventManager.getInstance().get(new CalendarInterval(from, to));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        for (
                to.add(Calendar.MONTH, -1), to.set(Calendar.DAY_OF_MONTH, 2);
                from.before(stop);
                from.add(Calendar.DAY_OF_MONTH, 1), to.add(Calendar.DAY_OF_MONTH, 1)
        ) {
            CalendarInterval interval = new CalendarInterval(from, to);
            int eventCount = (int) monthEventList.stream()
                    .filter(e -> interval.isIntersect(e.interval))
                    .count();
            if (eventCount > 0)
                eventDayList.add(
                        new EventDay(
                                (Calendar) from.clone(),
                                eventDayIcons[eventCount < 10 ? eventCount : 0]
                        )
                );
        }
        calendarView.setEvents(eventDayList);//очищение дней без событий????????
    }
}