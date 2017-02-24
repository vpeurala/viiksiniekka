package com.shipyard.domain.example;

import com.shipyard.domain.builder.TimeOfDayBuilder;

public class TimeOfDayExamples {
    public static TimeOfDayBuilder t0700() {
        return new TimeOfDayBuilder()
                .withHour(7)
                .withMinute(0);
    }

    public static TimeOfDayBuilder t1000() {
        return new TimeOfDayBuilder()
                .withHour(10)
                .withMinute(0);
    }

    public static TimeOfDayBuilder t1015() {
        return new TimeOfDayBuilder()
                .withHour(10)
                .withMinute(15);
    }

    public static TimeOfDayBuilder t1400() {
        return new TimeOfDayBuilder()
                .withHour(14)
                .withMinute(0);
    }

    public static TimeOfDayBuilder t1620() {
        return new TimeOfDayBuilder()
                .withHour(16)
                .withMinute(20);
    }

    public static TimeOfDayBuilder t1830() {
        return new TimeOfDayBuilder()
                .withHour(18)
                .withMinute(30);
    }
}
