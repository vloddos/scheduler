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

        from.setTime(Constants.fullDateFormat.parse("2019-03-17 15:35:00"));
        to.setTime(Constants.fullDateFormat.parse("2019-03-17 15:45:00"));
        eventList.add(
                new Event(
                        0,
                        "testevent",
                        new CalendarInterval(from, to),
                        "97wgfe99731fgg382ewfojscndjbsfvjkxcvxc"
                )
        );

        from.setTime(Constants.fullDateFormat.parse("2019-03-17 15:00:00"));
        to.setTime(Constants.fullDateFormat.parse("2019-03-17 15:10:00"));
        eventList.add(
                new Event(
                        0,
                        "second event",
                        new CalendarInterval(from, to),
                        "3749t0294t940gwrijsfvjbdn cln derbln coijeprogjv09wo4rsvofxblkrsgbb"
                )
        );

        from.setTime(Constants.fullDateFormat.parse("2019-03-17 15:02:28"));
        to.setTime(Constants.fullDateFormat.parse("2019-03-17 15:13:37"));
        eventList.add(
                new Event(
                        0,
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
    public ArrayList<Event> get(CalendarInterval interval) {
        return
                eventList.stream()
                        .filter(e -> e.interval.isIntersect(interval))
                        .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void update(Event event) {
        for (int i = 0; i < eventList.size(); ++i)
            if (eventList.get(i).getId() == event.getId())
                eventList.set(i, event);
    }

    @Override
    public void delete(int id) {
        eventList.removeIf(e -> e.getId() == id);
    }
}
