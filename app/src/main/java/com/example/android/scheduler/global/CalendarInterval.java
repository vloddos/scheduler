package com.example.android.scheduler.global;

import java.io.Serializable;
import java.util.Calendar;

public class CalendarInterval implements Serializable, Cloneable {

    private static final long serialVersionUID = -6987360434922826720L;

    private static boolean le(Calendar a, Calendar b) {
        return a.before(b) || a.equals(b);
    }

    private Calendar from;//inclusive
    private Calendar to;//exclusive

    public CalendarInterval(Calendar from, Calendar to) throws IllegalStateException {
        if (from.after(to))
            throw new IllegalStateException("from is greater then to");

        this.from = (Calendar) from.clone();
        this.to = (Calendar) to.clone();
    }

    public boolean isIntersect(CalendarInterval other) {
        /*return le(from, other.from) && le(other.from, to) || le(from, other.to) && le(other.to, to) ||
                le(other.from, from) && le(from, other.to) || le(other.from, to) && le(to, other.to);*/
        return le(from, other.from) && other.from.before(to) || from.before(other.to) && le(other.to, to) ||
                le(other.from, from) && from.before(other.to) || other.from.before(to) && le(to, other.to);
    }

    public Calendar getFrom() {
        return (Calendar) from.clone();
    }

    public Calendar getTo() {
        return (Calendar) to.clone();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new CalendarInterval(from, to);
    }
}
