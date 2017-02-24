package com.shipyard.domain.example;

import com.shipyard.domain.builder.WorkweekBuilder;

public class WorkweekExamples {
    public static WorkweekBuilder week48() {
        return new WorkweekBuilder()
                .withWeekNumber(48)
                .withMonday(WorkdayExamples.monday())
                .withWednesday(WorkdayExamples.wednesday())
                .withSunday(WorkdayExamples.sunday());
    }
}
