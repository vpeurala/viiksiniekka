package com.shipyard.domain.example;

import com.shipyard.domain.builder.WorkdayBuilder;

public class WorkdayExamples {
    public static WorkdayBuilder monday() {
        return new WorkdayBuilder()
                .withStartTime(TimeOfDayExamples.t1015())
                .withEndTime(TimeOfDayExamples.t1830());
    }

    public static WorkdayBuilder wednesday() {
        return new WorkdayBuilder()
                .withStartTime(TimeOfDayExamples.t0700())
                .withEndTime(TimeOfDayExamples.t1620());
    }

    public static WorkdayBuilder sunday() {
        return new WorkdayBuilder()
                .withStartTime(TimeOfDayExamples.t1000())
                .withEndTime(TimeOfDayExamples.t1400());
    }
}
