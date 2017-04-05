package com.shipyard.domain.example;

import com.shipyard.domain.builder.NotificationBuilder;
import com.shipyard.domain.data.NotificationStatus;

import static java.util.Arrays.asList;

public class NotificationExamples {
    public static NotificationBuilder notification1() {
        return new NotificationBuilder()
                .withStatus(NotificationStatus.APPROVED)
                .withYardContact(ContactPersonExamples.teroPackalenWithContactInformation())
                .withSiteForeman(ContactPersonExamples.villePeuralaWithContactInformation())
                .withAdditionalInformation("Sunday work is needed because we are behind schedule.")
                .withWorkWeek(WorkweekExamples.week48())
                .withWorkEntries(asList(
                        WorkEntryExamples.jurijAsAWelderInBuilding44Ship2(),
                        WorkEntryExamples.genadijAsAFitterInBuilding43()))
                .withLog(asList(
                        LogEntryExamples.creationOfNotification1(),
                        LogEntryExamples.sendingOfNotification1()));
    }
}
