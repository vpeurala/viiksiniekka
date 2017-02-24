package com.shipyard.domain.builder;

import com.shipyard.domain.data.TimeOfDay;

import java.util.Objects;

public class TimeOfDayBuilder {
    private Integer hour;
    private Integer minute;

    public static TimeOfDayBuilder from(TimeOfDay timeOfDay) {
        return new TimeOfDayBuilder()
                .withHour(timeOfDay.getHour())
                .withMinute(timeOfDay.getMinute());
    }

    /**
     * The hour using 24-hour clock, value is 0-23.
     */
    public TimeOfDayBuilder withHour(Integer hour) {
        Objects.requireNonNull(hour);
        this.hour = hour;
        return this;
    }

    /**
     * The minute, value is 0-59.
     */
    public TimeOfDayBuilder withMinute(Integer minute) {
        Objects.requireNonNull(minute);
        this.minute = minute;
        return this;
    }

    public TimeOfDay build() {
        return new TimeOfDay(hour, minute);
    }
}
