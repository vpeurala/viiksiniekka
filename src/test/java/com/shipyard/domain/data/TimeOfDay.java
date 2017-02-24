package com.shipyard.domain.data;

import java.util.Objects;

/**
 * The time of day without a date.
 */
public class TimeOfDay {
    private final Integer hour;
    private final Integer minute;

    public TimeOfDay(
            Integer hour,
            Integer minute) {
        Objects.requireNonNull(hour, "Field 'hour' of class TimeOfDay cannot be null.");
        Objects.requireNonNull(minute, "Field 'minute' of class TimeOfDay cannot be null.");
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * The hour using 24-hour clock, value is 0-23.
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * The minute, value is 0-59.
     */
    public Integer getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        return "TimeOfDay{"
                + "hour='" + getHour() + "'"
                + ", " + "minute='" + getMinute() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeOfDay other = (TimeOfDay) o;

        if (!getHour().equals(other.getHour())) return false;
        if (!getMinute().equals(other.getMinute())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getHour().hashCode();
        result = 31 * result + getMinute().hashCode();
        return result;
    }
}
