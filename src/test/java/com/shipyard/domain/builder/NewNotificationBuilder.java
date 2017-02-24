package com.shipyard.domain.builder;

import com.shipyard.domain.data.NewNotification;
import com.shipyard.domain.data.NotificationStatus;
import com.shipyard.domain.data.Workweek;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NewNotificationBuilder {
    private NotificationStatus status;
    private Long yardContact;
    private Long siteForeman;
    private String additionalInformation;
    private WorkweekBuilder workWeek = new WorkweekBuilder();
    private List<NewWorkEntryBuilder> workEntries = new ArrayList<>();

    public static NewNotificationBuilder from(NewNotification newNotification) {
        return new NewNotificationBuilder()
                .withStatus(newNotification.getStatus())
                .withYardContact(newNotification.getYardContact())
                .withSiteForeman(newNotification.getSiteForeman())
                .withAdditionalInformation(newNotification.getAdditionalInformation())
                .withWorkWeek(newNotification.getWorkWeek())
                .withWorkEntries(newNotification.getWorkEntries().stream().map(NewWorkEntryBuilder::from).collect(Collectors.toList()));
    }

    /**
     * The state of the notification.
     */
    public NewNotificationBuilder withStatus(NotificationStatus status) {
        Objects.requireNonNull(status);
        this.status = status;
        return this;
    }

    /**
     * The responsible person about this notification on the yard side.
     */
    public NewNotificationBuilder withYardContact(Long yardContact) {
        Objects.requireNonNull(yardContact);
        this.yardContact = yardContact;
        return this;
    }

    /**
     * The site foreman who is on the premises supervising the work.
     */
    public NewNotificationBuilder withSiteForeman(Long siteForeman) {
        Objects.requireNonNull(siteForeman);
        this.siteForeman = siteForeman;
        return this;
    }

    /**
     * Comments and additional info.
     */
    public NewNotificationBuilder withAdditionalInformation(String additionalInformation) {
        Objects.requireNonNull(additionalInformation);
        this.additionalInformation = additionalInformation;
        return this;
    }

    /**
     * The working week this notification is about.
     */
    public WorkweekBuilder getWorkWeek() {
        return workWeek;
    }

    /**
     * The working week this notification is about.
     */
    public NewNotificationBuilder withWorkWeek(WorkweekBuilder workWeek) {
        Objects.requireNonNull(workWeek);
        this.workWeek = workWeek;
        return this;
    }

    /**
     * The working week this notification is about.
     */
    public NewNotificationBuilder withWorkWeek(Workweek workWeek) {
        Objects.requireNonNull(workWeek);
        this.workWeek = WorkweekBuilder.from(workWeek);
        return this;
    }

    /**
     * Entries about each worker, his/her location and energy requirements.
     */
    public List<NewWorkEntryBuilder> getWorkEntries() {
        return workEntries;
    }

    /**
     * Entries about each worker, his/her location and energy requirements.
     */
    public NewNotificationBuilder withWorkEntries(List<NewWorkEntryBuilder> workEntries) {
        Objects.requireNonNull(workEntries);
        this.workEntries = workEntries;
        return this;
    }

    public NewNotification build() {
        return new NewNotification(status, yardContact, siteForeman, additionalInformation, workWeek.build(), workEntries.stream().map(NewWorkEntryBuilder::build).collect(Collectors.toList()));
    }
}
