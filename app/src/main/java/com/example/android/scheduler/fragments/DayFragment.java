package com.example.android.scheduler.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.android.scheduler.R;
import com.example.android.scheduler.activities.EventActivity;
import com.example.android.scheduler.activities.MainActivity;
import com.example.android.scheduler.activities.RequestCodes;
import com.example.android.scheduler.client.StubEventManager;
import com.example.android.scheduler.fragments.adapters.HourExpandableListAdapter;
import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.global.Global;
import com.example.android.scheduler.models.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayFragment extends Fragment implements Selectable, EventManageable {

    public static final String LOG_TAG = DayFragment.class.getSimpleName();

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

    public TextView dayOfWeek;
    public TextView date;

    public ExpandableListView events;

    public Button leftButton;
    public Button rightButton;

    public DayFragment() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        dayOfWeek = view.findViewById(R.id.day_of_week);
        date = view.findViewById(R.id.date);
        events = view.findViewById(R.id.events);

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
        ArrayList<ArrayList<Event>> groups = new ArrayList<>();

        for (int i = 0; i < 24; ++i) {
            groups.add(new ArrayList<>());

            from.set(Calendar.HOUR_OF_DAY, i);
            to.set(Calendar.HOUR_OF_DAY, i + 1);
            CalendarInterval interval = new CalendarInterval(from, to);

            for (Event e : dayEventList)
                if (interval.isIntersect(e.interval))
                    groups.get(i).add(e);// FIXME: 18.03.2019 clone???????????
        }

        events.setAdapter(
                new HourExpandableListAdapter(
                        getActivity(),
                        groups,
                        event -> {
                            Intent intent = new Intent(getActivity(), EventActivity.class);
                            intent.putExtra("event", event);
                            startActivityForResult(intent, RequestCodes.UPDATE_EVENT);
                        }
                )
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(LOG_TAG, "onActivityResult from DayFragment was called, request code:" + requestCode);
        if (data != null)
            switch (requestCode) {
                case RequestCodes.REMOVE_EVENT:
                    removeEvent(data.getIntExtra("id", -1));
                    break;
                case RequestCodes.UPDATE_EVENT:
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
        Calendar from = (Calendar) Global.selectedCalendar.clone();//visible day is always selected
        from.set(from.get(Calendar.YEAR), from.get(Calendar.MONTH), from.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        from.set(Calendar.MILLISECOND, 0);
        Calendar to = (Calendar) from.clone();
        to.add(Calendar.DAY_OF_YEAR, 1);
        return new CalendarInterval(from, to);
    }

    public void change(View view) {
        Global.selectedCalendar.add(Calendar.DAY_OF_MONTH, view.getId() == leftButton.getId() ? -1 : 1);//Calendar.DAY_OF_YEAR???
        select();
        setEvents(StubEventManager.getInstance().get(getVisibleInterval()));
    }
}