package com.example.android.scheduler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
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

public class MonthFragment extends Fragment implements Selectable, EventManageable {

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
        OnCalendarPageChangeListener onCalendarPageChangeListener = () -> setEvents(StubEventManager.getInstance().get(getVisibleInterval()));
        calendarView.setOnPreviousPageChangeListener(onCalendarPageChangeListener);
        calendarView.setOnForwardPageChangeListener(onCalendarPageChangeListener);
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
        onCalendarPageChangeListener.onChange();//костыль?

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

    private List<Event> monthEventList;//добавить возможность перейти в eventlistactivity?

    @Override
    public void setEvents(List<Event> eventList) {
        /*Calendar from = calendarView.getCurrentPageDate();
        Calendar to = (Calendar) from.clone();
        to.add(Calendar.MONTH, 1);
        Calendar stop = (Calendar) to.clone();
        List<EventDay> eventDayList = new ArrayList<>();

        try {
            monthEventList = StubEventManager.getInstance().get(new CalendarInterval(from, to));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }*/
        monthEventList = eventList;
        CalendarInterval calendarInterval = getVisibleInterval();
        Calendar
                from = calendarInterval.getFrom(),
                to = (Calendar) from.clone(),
                stop = calendarInterval.getTo();
        List<EventDay> eventDayList = new ArrayList<>();

        for (
//                to.add(Calendar.MONTH, -1), to.set(Calendar.DAY_OF_MONTH, 2);
                to.set(Calendar.DAY_OF_MONTH, 2);
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

    @Override
    public void addEvent(Event event) {
        monthEventList.add(event);
        setEvents(monthEventList);
    }

    @Override
    public void removeEvent(int id) {
        for (int i = 0; i < monthEventList.size(); ++i)
            if (monthEventList.get(i).getId() == id) {
                monthEventList.remove(i);
                break;
            }
        setEvents(monthEventList);
    }

    @Override
    public void updateEvent(Event event) {
        if (event.interval.isIntersect(getVisibleInterval())) {
            for (int i = 0; i < monthEventList.size(); ++i)
                if (monthEventList.get(i).getId() == event.getId()) {
                    monthEventList.set(i, event);
                    break;
                }
            setEvents(monthEventList);//????????
        } else
            removeEvent(event.getId());
    }

    @Override
    public CalendarInterval getVisibleInterval() {
        Calendar from = calendarView.getCurrentPageDate();
        Calendar to = (Calendar) from.clone();
        to.add(Calendar.MONTH, 1);
        return new CalendarInterval(from, to);
    }
}