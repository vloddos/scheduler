package com.example.android.scheduler.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.android.scheduler.R;
import com.example.android.scheduler.activities.EventActivity;
import com.example.android.scheduler.activities.MainActivity;
import com.example.android.scheduler.client.StubEventManager;
import com.example.android.scheduler.fragments.adapters.HourExpandableListAdapter;
import com.example.android.scheduler.global.BiHashMap;
import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.global.Global;
import com.example.android.scheduler.models.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

public class WeekFragment extends Fragment implements Selectable, EventManageable {

    public static final String title = "week";

    private Calendar currentPageCalendar;
    private View selectedView;
    private BiHashMap<Integer, View> day_date = new BiHashMap<>();

    private ExpandableListView eventsMonday;
    private ExpandableListView eventsTuesday;
    private ExpandableListView eventsWednesday;
    private ExpandableListView eventsThursday;
    private ExpandableListView eventsFriday;
    private ExpandableListView eventsSaturday;
    private ExpandableListView eventsSunday;

    private View dateMonday;
    private View dateTuesday;
    private View dateWednesday;
    private View dateThursday;
    private View dateFriday;
    private View dateSaturday;
    private View dateSunday;

    private Button leftButton;
    private Button rightButton;

    private Consumer<View> selectDayOfWeek = v -> {
        Global.selectedCalendar.set(Calendar.DAY_OF_WEEK, day_date.reverseGet(v));
        if (selectedView != null) {
            selectedView.setBackgroundColor(Color.TRANSPARENT);
            ((TextView) selectedView.findViewById(R.id.day)).setTextColor(Color.BLACK);
            ((TextView) selectedView.findViewById(R.id.date)).setTextColor(Color.BLACK);
        }
        v.setBackgroundColor(Color.BLUE);
        ((TextView) v.findViewById(R.id.day)).setTextColor(Color.WHITE);
        ((TextView) v.findViewById(R.id.date)).setTextColor(Color.WHITE);
        selectedView = v;
    };

    public WeekFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).weekFragment = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        eventsMonday = view.findViewById(R.id.eventsMonday);
        eventsTuesday = view.findViewById(R.id.eventsTuesday);
        eventsWednesday = view.findViewById(R.id.eventsWednesday);
        eventsThursday = view.findViewById(R.id.eventsThursday);
        eventsFriday = view.findViewById(R.id.eventsFriday);
        eventsSaturday = view.findViewById(R.id.eventsSaturday);
        eventsSunday = view.findViewById(R.id.eventsSunday);

        dateMonday = view.findViewById(R.id.dateMonday);
        dateMonday.setOnClickListener(
                v -> {
                    selectDayOfWeek.accept(dateMonday);//v???
                    if (eventsMonday.getVisibility() == View.VISIBLE)
                        eventsMonday.setVisibility(View.GONE);
                    else
                        eventsMonday.setVisibility(View.VISIBLE);
                }
        );
        dateTuesday = view.findViewById(R.id.dateTuesday);
        dateTuesday.setOnClickListener(
                v -> {
                    selectDayOfWeek.accept(dateTuesday);
                    if (eventsTuesday.getVisibility() == View.VISIBLE)
                        eventsTuesday.setVisibility(View.GONE);
                    else
                        eventsTuesday.setVisibility(View.VISIBLE);
                }
        );
        dateWednesday = view.findViewById(R.id.dateWednesday);
        dateWednesday.setOnClickListener(
                v -> {
                    selectDayOfWeek.accept(dateWednesday);
                    if (eventsWednesday.getVisibility() == View.VISIBLE)
                        eventsWednesday.setVisibility(View.GONE);
                    else
                        eventsWednesday.setVisibility(View.VISIBLE);
                }
        );
        dateThursday = view.findViewById(R.id.dateThursday);
        dateThursday.setOnClickListener(
                v -> {
                    selectDayOfWeek.accept(dateThursday);
                    if (eventsThursday.getVisibility() == View.VISIBLE)
                        eventsThursday.setVisibility(View.GONE);
                    else
                        eventsThursday.setVisibility(View.VISIBLE);
                }
        );
        dateFriday = view.findViewById(R.id.dateFriday);
        dateFriday.setOnClickListener(
                v -> {
                    selectDayOfWeek.accept(dateFriday);
                    if (eventsFriday.getVisibility() == View.VISIBLE)
                        eventsFriday.setVisibility(View.GONE);
                    else
                        eventsFriday.setVisibility(View.VISIBLE);
                }
        );
        dateSaturday = view.findViewById(R.id.dateSaturday);
        dateSaturday.setOnClickListener(
                v -> {
                    selectDayOfWeek.accept(dateSaturday);
                    if (eventsSaturday.getVisibility() == View.VISIBLE)
                        eventsSaturday.setVisibility(View.GONE);
                    else
                        eventsSaturday.setVisibility(View.VISIBLE);
                }
        );
        dateSunday = view.findViewById(R.id.dateSunday);
        dateSunday.setOnClickListener(
                v -> {
                    selectDayOfWeek.accept(dateSunday);
                    if (eventsSunday.getVisibility() == View.VISIBLE)
                        eventsSunday.setVisibility(View.GONE);
                    else
                        eventsSunday.setVisibility(View.VISIBLE);
                }
        );

        day_date.put(Calendar.MONDAY, dateMonday);
        day_date.put(Calendar.TUESDAY, dateTuesday);
        day_date.put(Calendar.WEDNESDAY, dateWednesday);
        day_date.put(Calendar.THURSDAY, dateThursday);
        day_date.put(Calendar.FRIDAY, dateFriday);
        day_date.put(Calendar.SATURDAY, dateSaturday);
        day_date.put(Calendar.SUNDAY, dateSunday);

        leftButton = view.findViewById(R.id.leftButton);
        leftButton.setOnClickListener(this::change);
        rightButton = view.findViewById(R.id.rightButton);
        rightButton.setOnClickListener(this::change);

        return view;
    }

    @Override
    public void select() {
        currentPageCalendar = (Calendar) Global.selectedCalendar.clone();
        currentPageCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        currentPageCalendar.set(
                currentPageCalendar.get(Calendar.YEAR),
                currentPageCalendar.get(Calendar.MONTH),
                currentPageCalendar.get(Calendar.DAY_OF_MONTH),
                0,
                0,
                0
        );
        currentPageCalendar.set(Calendar.MILLISECOND, 0);//redundant???
        selectDayOfWeek.accept(day_date.get(Global.selectedCalendar.get(Calendar.DAY_OF_WEEK)));
    }

    private List<Event> weekEventList;

    @Override
    public void setEvents(List<Event> eventList) {
        weekEventList = eventList;
        int i, j;
        Calendar from = getVisibleInterval().getFrom(), to;

        ArrayList<String> daysOfWeek1 = new ArrayList<>(
                Arrays.asList(
                        "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
                )
        );
        ArrayList<Integer> daysOfWeek2 = new ArrayList<>(
                Arrays.asList(
                        Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
                        Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY
                )
        );
        ArrayList<ExpandableListView> listViews = new ArrayList<>(
                Arrays.asList(
                        eventsMonday, eventsTuesday, eventsWednesday,
                        eventsThursday, eventsFriday, eventsSaturday, eventsSunday
                )
        );
        ArrayList<View> dateViews = new ArrayList<>(
                Arrays.asList(
                        dateMonday, dateTuesday, dateWednesday,
                        dateThursday, dateFriday, dateSaturday, dateSunday
                )
        );

        for (i = 0; i < 7; ++i) {
            ArrayList<ArrayList<Event>> children = new ArrayList<>();
            from.set(Calendar.DAY_OF_WEEK, daysOfWeek2.get(i));
            to = (Calendar) from.clone();
            ((TextView) dateViews.get(i).findViewById(R.id.day)).setText(daysOfWeek1.get(i));
            ((TextView) dateViews.get(i).findViewById(R.id.date)).setText(Constants.shortDateFormat.format(from.getTime()));

            for (j = 0; j < 24; ++j) {
                children.add(new ArrayList<>());

                from.set(Calendar.HOUR_OF_DAY, j);
                to.set(Calendar.HOUR_OF_DAY, j + 1);
                CalendarInterval interval = new CalendarInterval(from, to);

                for (Event e : weekEventList)
                    if (interval.isIntersect(e.interval))
                        children.get(j).add(e);
            }

            listViews.get(i).setAdapter(
                    new HourExpandableListAdapter(
                            getActivity(),
                            children,
                            event -> {
                                Intent intent = new Intent(getActivity(), EventActivity.class);
                                intent.putExtra("event", event);
                                startActivityForResult(intent, MainActivity.UPDATE_EVENT_REQUEST);
                            }
                    )
            );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
            switch (requestCode) {
                case MainActivity.REMOVE_EVENT_REQUEST:
                    removeEvent(data.getIntExtra("id", -1));
                    break;
                case MainActivity.UPDATE_EVENT_REQUEST:
                    Event event = (Event) data.getSerializableExtra("event");
                    if (event == null)//for remove button in event activity костыль?
                        removeEvent(data.getIntExtra("id", -1));
                    else
                        updateEvent(event);
                    break;
            }
    }

    @Override
    public void addEvent(Event event) {
        weekEventList.add(event);
        setEvents(weekEventList);
    }

    @Override
    public void removeEvent(int id) {
        for (int i = 0; i < weekEventList.size(); ++i)
            if (weekEventList.get(i).getId() == id) {
                weekEventList.remove(i);
                break;
            }
        setEvents(weekEventList);
    }

    @Override
    public void updateEvent(Event event) {
        if (event.interval.isIntersect(getVisibleInterval())) {
            for (int i = 0; i < weekEventList.size(); ++i)
                if (weekEventList.get(i).getId() == event.getId()) {
                    weekEventList.set(i, event);
                    break;
                }
            setEvents(weekEventList);//????????
        } else
            removeEvent(event.getId());
    }

    @Override
    public CalendarInterval getVisibleInterval() {
        Calendar to = (Calendar) currentPageCalendar.clone();
        to.add(Calendar.WEEK_OF_YEAR, 1);
        return new CalendarInterval(currentPageCalendar, to);
    }

    public void change(View view) {
        currentPageCalendar.add(Calendar.WEEK_OF_YEAR, view.getId() == leftButton.getId() ? -1 : 1);
        setEvents(StubEventManager.getInstance().get(getVisibleInterval()));
    }
}