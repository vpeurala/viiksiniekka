package com.shipyard.domain.example;

import com.shipyard.domain.builder.LogEntryBuilder;
import com.shipyard.domain.data.NotificationAction;

import java.time.LocalDateTime;

public class LogEntryExamples {
    public static LogEntryBuilder creationOfNotification1() {
        return new LogEntryBuilder()
                .withAction(NotificationAction.CREATE)
                .withCreatedAt(LocalDateTime.of(2015, 1, 16, 21, 59))
                .withCreatedBy(UserExamples.teroPackalenYardCom());
    }

    public static LogEntryBuilder sendingOfNotification1() {
        return new LogEntryBuilder()
                .withAction(NotificationAction.SEND)
                .withCreatedAt(LocalDateTime.of(2015, 1, 16, 22, 5))
                .withCreatedBy(UserExamples.teroPackalenYardCom());
    }
}
