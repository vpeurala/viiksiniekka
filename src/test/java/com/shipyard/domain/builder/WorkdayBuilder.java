package com.shipyard.domain.builder;

import com.shipyard.domain.data.TimeOfDay;
import com.shipyard.domain.data.Workday;

import java.util.Objects;

public class WorkdayBuilder {
    private TimeOfDayBuilder startTime = new TimeOfDayBuilder();
    private TimeOfDayBuilder endTime = new TimeOfDayBuilder();

    public static WorkdayBuilder from(Workday workday) {
        return new WorkdayBuilder()
                .withStartTime(workday.getStartTime())
                .withEndTime(workday.getEndTime());
    }

    /**
     * Starting time of the workday without time zone.
     */
    public TimeOfDayBuilder getStartTime() {
        return startTime;
    }

    /**
     * Starting time of the workday without time zone.
     */
    public WorkdayBuilder withStartTime(TimeOfDayBuilder startTime) {
        Objects.requireNonNull(startTime);
        this.startTime = startTime;
        return this;
    }

    /**
     * Starting time of the workday without time zone.
     */
    public WorkdayBuilder withStartTime(TimeOfDay startTime) {
        Objects.requireNonNull(startTime);
        this.startTime = TimeOfDayBuilder.from(startTime);
        return this;
    }

    /**
     * Ending time of the workday without time zone.
     */
    public TimeOfDayBuilder getEndTime() {
        return endTime;
    }

    /**
     * Ending time of the workday without time zone.
     */
    public WorkdayBuilder withEndTime(TimeOfDayBuilder endTime) {
        Objects.requireNonNull(endTime);
        this.endTime = endTime;
        return this;
    }

    /**
     * Ending time of the workday without time zone.
     */
    public WorkdayBuilder withEndTime(TimeOfDay endTime) {
        Objects.requireNonNull(endTime);
        this.endTime = TimeOfDayBuilder.from(endTime);
        return this;
    }

    public Workday build() {
        return new Workday(startTime.build(), endTime.build());
    }
}
