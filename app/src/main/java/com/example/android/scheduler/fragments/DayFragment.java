package com.example.android.scheduler.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.scheduler.R;
import com.example.android.scheduler.activities.MainActivity;
import com.example.android.scheduler.client.StubEventManager;
import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.global.Global;
import com.example.android.scheduler.models.Event;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class DayFragment extends Fragment implements Selectable, EventManageable {

    public static final String title = "day";

    private static final SparseArray<String> daysOfWeek = new SparseArray<>();

    // TODO: 18.03.2019 use locale
    static {
        daysOfWeek.put(Calendar.MONDAY, "пн");
        daysOfWeek.put(Calendar.TUESDAY, "вт");
        daysOfWeek.put(Calendar.WEDNESDAY, "ср");
        daysOfWeek.put(Calendar.THURSDAY, "чт");
        daysOfWeek.put(Calendar.FRIDAY, "пт");
        daysOfWeek.put(Calendar.SATURDAY, "сб");
        daysOfWeek.put(Calendar.SUNDAY, "вс");
    }

    private ArrayList<Event>[] hourEventLists = new ArrayList[24];

    public TextView dayOfWeek;
    public TextView date;

    public LinearLayout hours;

    public Button leftButton;
    public Button rightButton;

    public DayFragment() {
        for (int i = 0; i < hourEventLists.length; ++i)
            hourEventLists[i] = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).dayFragment = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        dayOfWeek = view.findViewById(R.id.day_of_week);
        date = view.findViewById(R.id.date);

        hours = view.findViewById(R.id.hours);

        leftButton = view.findViewById(R.id.leftButton);
        leftButton.setOnClickListener(this::change);

        rightButton = view.findViewById(R.id.rightButton);
        rightButton.setOnClickListener(this::change);

        return view;
    }

    @Override
    public void select() {
        Calendar calendar = Global.selectedCalendar;
        dayOfWeek.setText(daysOfWeek.get(calendar.get(Calendar.DAY_OF_WEEK)));
        date.setText(Constants.shortDateFormat.format(calendar.getTime()));
    }

    private List<Event> dayEventList;

    @Override
    public void setEvents(List<Event> eventList) {//must call after select
        dayEventList = eventList;
        CalendarInterval calendarInterval = getVisibleInterval();
        Calendar from = calendarInterval.getFrom(), to = (Calendar) from.clone();

        for (int i = 0; i < hours.getChildCount(); ++i) {
            TextView h = (TextView) hours.getChildAt(i);

            from.set(Calendar.HOUR_OF_DAY, i);
            to.set(Calendar.HOUR_OF_DAY, i + 1);
            CalendarInterval interval = new CalendarInterval(from, to);

            hourEventLists[i].clear();

            for (Event e : dayEventList)
                if (interval.isIntersect(e.interval))
                    hourEventLists[i].add(e);// FIXME: 18.03.2019 clone???????????

            String text = hourEventLists[i].stream()
                    .map(e -> e.name)
                    .collect(Collectors.joining(";"));

            if (text.length() > 50)
                text = text.substring(0, 47) + "...";

            h.setText(text);
            if (text.equals(""))
                h.setBackgroundColor(Color.TRANSPARENT);
            else {
                h.setTextColor(Color.WHITE);
                h.setBackgroundColor(Color.BLUE);
            }
        }
    }

    @Override
    public void addEvent(Event event) {
        dayEventList.add(event);
        setEvents(dayEventList);
    }

    @Override
    public void removeEvent(int id) {
        for (int i = 0; i < dayEventList.size(); ++i)
            if (dayEventList.get(i).getId() == id) {
                dayEventList.remove(i);
                break;
            }
        setEvents(dayEventList);
    }

    @Override
    public void updateEvent(Event event) {
        if (event.interval.isIntersect(getVisibleInterval())) {
            for (int i = 0; i < dayEventList.size(); ++i)
                if (dayEventList.get(i).getId() == event.getId()) {
                    dayEventList.set(i, event);
                    break;
                }
            setEvents(dayEventList);//????????
        } else
            removeEvent(event.getId());
    }

    @Override
    public CalendarInterval getVisibleInterval() {
        Calendar from = Calendar.getInstance();
        try {
            from.setTime(Constants.shortDateFormat.parse(date.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Calendar to = (Calendar) from.clone();
        to.add(Calendar.DAY_OF_YEAR, 1);
        return new CalendarInterval(from, to);
    }

    public void change(View view) {
        Global.selectedCalendar.add(Calendar.DAY_OF_MONTH, view.getId() == leftButton.getId() ? -1 : 1);//Calendar.DAY_OF_YEAR???

        select();
        setEvents(StubEventManager.getInstance().get(getVisibleInterval()));
    }

    public Calendar getCalendar(View view) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Constants.shortDateFormat.parse(date.getText().toString()));
        calendar.set(Calendar.HOUR_OF_DAY, hours.indexOfChild(view));
        return calendar;
    }

    public ArrayList<Event> getEvents(View view) {
        return hourEventLists[hours.indexOfChild(view)];
    }
}
