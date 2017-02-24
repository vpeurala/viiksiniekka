package com.shipyard.domain.data;

import java.util.Objects;

/**
 * The working time of one day.
 */
public class Workday {
    private final TimeOfDay startTime;
    private final TimeOfDay endTime;

    public Workday(
            TimeOfDay startTime,
            TimeOfDay endTime) {
        Objects.requireNonNull(startTime, "Field 'startTime' of class Workday cannot be null.");
        Objects.requireNonNull(endTime, "Field 'endTime' of class Workday cannot be null.");
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Starting time of the workday without time zone.
     */
    public TimeOfDay getStartTime() {
        return startTime;
    }

    /**
     * Ending time of the workday without time zone.
     */
    public TimeOfDay getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Workday{"
                + "startTime='" + getStartTime() + "'"
                + ", " + "endTime='" + getEndTime() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Workday other = (Workday) o;

        if (!getStartTime().equals(other.getStartTime())) return false;
        if (!getEndTime().equals(other.getEndTime())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getStartTime().hashCode();
        result = 31 * result + getEndTime().hashCode();
        return result;
    }
}
