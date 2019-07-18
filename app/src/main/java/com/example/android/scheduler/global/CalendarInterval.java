package com.example.android.scheduler.global;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

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
        return le(from, other.from) && other.from.before(to) || from.before(other.to) && le(other.to, to) ||
                le(other.from, from) && from.before(other.to) || other.from.before(to) && le(to, other.to);
    }

    public Calendar getFrom() {
        return (Calendar) from.clone();
    }

    public Calendar getTo() {
        return (Calendar) to.clone();
    }

    public void setFromTimeZone(TimeZone timeZone) {
        from.setTimeZone(timeZone);
    }

    public void setToTimeZone(TimeZone timeZone) {
        to.setTimeZone(timeZone);
    }

    public TimeZone getFromTimeZone() {
        return getFrom().getTimeZone();
    }

    public TimeZone getToTimeZone() {
        return getTo().getTimeZone();
    }

    @Override
    public Object clone() {
        try {
            CalendarInterval calendarInterval = (CalendarInterval) super.clone();
            calendarInterval.from = (Calendar) from.clone();
            calendarInterval.to = (Calendar) to.clone();
            return calendarInterval;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
}