package com.example.android.scheduler.client;

import com.example.android.scheduler.global.CalendarInterval;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.models.Event;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class StubEventManager implements EventService {

    private static StubEventManager instance;

    static {
        try {
            instance = new StubEventManager();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static StubEventManager getInstance() {
        return instance;
    }

    private List<Event> eventList = new ArrayList<>();

    private StubEventManager() throws ParseException {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();

        from.set(Calendar.HOUR_OF_DAY, 15);
        from.set(Calendar.MINUTE, 35);
        to.set(Calendar.HOUR_OF_DAY, 15);
        to.set(Calendar.MINUTE, 45);
        eventList.add(
                new Event(
                        1337,
                        "testevent",
                        new CalendarInterval(from, to),
                        "97wgfe99731fgg382ewfojscndjbsfvjkxcvxc"
                )
        );

        from.set(Calendar.HOUR_OF_DAY, 15);
        from.set(Calendar.MINUTE, 0);
        to.set(Calendar.HOUR_OF_DAY, 15);
        to.set(Calendar.MINUTE, 10);
        eventList.add(
                new Event(
                        228,
                        "second event",
                        new CalendarInterval(from, to),
                        "3749t0294t940gwrijsfvjbdn cln derbln coijeprogjv09wo4rsvofxblkrsgbb"
                )
        );

        from.set(Calendar.HOUR_OF_DAY, 15);
        from.set(Calendar.MINUTE, 2);
        to.set(Calendar.HOUR_OF_DAY, 15);
        to.set(Calendar.MINUTE, 13);
        eventList.add(
                new Event(
                        1488,
                        "event with long name",
                        new CalendarInterval(from, to),
                        "3749t0294t940gwrijsfvjbdn cln derbln coijeprogjv09wo4rsvofxblkrsgbb" +
                                "3749t0294t940gwrijsfvjbdn cln derbln coijeprogjv09wo4rsvofxblkrsgbb" +
                                "3749t0294t940gwrijsfvjbdn cln derbln coijeprogjv09wo4rsvofxblkrsgbb" +
                                "3749t0294t940gwrijsfvjbdn cln derbln coijeprogjv09wo4rsvofxblkrsgbb"
                )
        );
    }


    @Override
    public void add(Event event) {
        eventList.add(event);
    }

    @Override
    public List<Event> get(CalendarInterval interval) {// FIXME: 18.03.2019 remove clone?
        /*List<Event> el = new ArrayList<>();
        for (Event e : eventList)
            if (e.interval.isIntersect(interval))
                el.add((Event) e.clone());

        return el;*/
        return
                eventList.stream()
                        .filter(e -> e.interval.isIntersect(interval))
                        .collect(Collectors.toList());
    }

    @Override
    public void update(Event event) {
        for (int i = 0; i < eventList.size(); ++i)
            if (eventList.get(i).getId() == event.getId()) {
                eventList.set(i, event);
                break;
            }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < eventList.size(); ++i)
            if (eventList.get(i).getId() == id) {
                eventList.remove(i);
                break;
            }
    }
}
