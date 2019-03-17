package com.example.android.scheduler.global;

import java.util.Calendar;

public class CalendarInterval {

    private static boolean le(Calendar a, Calendar b) {
        return a.before(b) || a.equals(b);
    }

    private Calendar from;//inclusive
    private Calendar to;//inclusive

    public CalendarInterval(Calendar from, Calendar to) throws IllegalStateException {
        if (from.after(to))
            throw new IllegalStateException("from is greater then to");

        this.from = (Calendar) from.clone();
        this.to = (Calendar) to.clone();
    }

    public boolean isIntersect(CalendarInterval other) {
        return le(from, other.from) && le(other.from, to) || le(from, other.to) && le(other.to, to) ||
                le(other.from, from) && le(from, other.to) || le(other.from, to) && le(to, other.to);
    }

    public Calendar getFrom() {
        return (Calendar) from.clone();
    }

    public Calendar getTo() {
        return (Calendar) to.clone();
    }
}
