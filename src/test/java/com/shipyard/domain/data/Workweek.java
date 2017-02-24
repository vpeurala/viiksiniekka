package com.shipyard.domain.data;

import java.util.Objects;
import java.util.Optional;

/**
 * A working week number and days with times.
 */
public class Workweek {
    private final Integer weekNumber;
    private final Optional<Workday> monday;
    private final Optional<Workday> tuesday;
    private final Optional<Workday> wednesday;
    private final Optional<Workday> thursday;
    private final Optional<Workday> friday;
    private final Optional<Workday> saturday;
    private final Optional<Workday> sunday;

    public Workweek(
            Integer weekNumber,
            Optional<Workday> monday,
            Optional<Workday> tuesday,
            Optional<Workday> wednesday,
            Optional<Workday> thursday,
            Optional<Workday> friday,
            Optional<Workday> saturday,
            Optional<Workday> sunday) {
        Objects.requireNonNull(weekNumber, "Field 'weekNumber' of class Workweek cannot be null.");
        Objects.requireNonNull(monday, "Field 'monday' of class Workweek cannot be null.");
        Objects.requireNonNull(tuesday, "Field 'tuesday' of class Workweek cannot be null.");
        Objects.requireNonNull(wednesday, "Field 'wednesday' of class Workweek cannot be null.");
        Objects.requireNonNull(thursday, "Field 'thursday' of class Workweek cannot be null.");
        Objects.requireNonNull(friday, "Field 'friday' of class Workweek cannot be null.");
        Objects.requireNonNull(saturday, "Field 'saturday' of class Workweek cannot be null.");
        Objects.requireNonNull(sunday, "Field 'sunday' of class Workweek cannot be null.");
        this.weekNumber = weekNumber;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    /**
     * A ISO week number, minimum is 1, maximum is 53.
     */
    public Integer getWeekNumber() {
        return weekNumber;
    }

    /**
     *
     */
    public Optional<Workday> getMonday() {
        return monday;
    }

    /**
     *
     */
    public Optional<Workday> getTuesday() {
        return tuesday;
    }

    /**
     *
     */
    public Optional<Workday> getWednesday() {
        return wednesday;
    }

    /**
     *
     */
    public Optional<Workday> getThursday() {
        return thursday;
    }

    /**
     *
     */
    public Optional<Workday> getFriday() {
        return friday;
    }

    /**
     *
     */
    public Optional<Workday> getSaturday() {
        return saturday;
    }

    /**
     *
     */
    public Optional<Workday> getSunday() {
        return sunday;
    }

    @Override
    public String toString() {
        return "Workweek{"
                + "weekNumber='" + getWeekNumber() + "'"
                + ", " + "monday='" + getMonday() + "'"
                + ", " + "tuesday='" + getTuesday() + "'"
                + ", " + "wednesday='" + getWednesday() + "'"
                + ", " + "thursday='" + getThursday() + "'"
                + ", " + "friday='" + getFriday() + "'"
                + ", " + "saturday='" + getSaturday() + "'"
                + ", " + "sunday='" + getSunday() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Workweek other = (Workweek) o;

        if (!getWeekNumber().equals(other.getWeekNumber())) return false;
        if (!getMonday().equals(other.getMonday())) return false;
        if (!getTuesday().equals(other.getTuesday())) return false;
        if (!getWednesday().equals(other.getWednesday())) return false;
        if (!getThursday().equals(other.getThursday())) return false;
        if (!getFriday().equals(other.getFriday())) return false;
        if (!getSaturday().equals(other.getSaturday())) return false;
        if (!getSunday().equals(other.getSunday())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getWeekNumber().hashCode();
        result = 31 * result + getMonday().hashCode();
        result = 31 * result + getTuesday().hashCode();
        result = 31 * result + getWednesday().hashCode();
        result = 31 * result + getThursday().hashCode();
        result = 31 * result + getFriday().hashCode();
        result = 31 * result + getSaturday().hashCode();
        result = 31 * result + getSunday().hashCode();
        return result;
    }
}
