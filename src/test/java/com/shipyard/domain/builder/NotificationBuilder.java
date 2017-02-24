package com.shipyard.domain.builder;

import com.shipyard.domain.data.ContactPerson;
import com.shipyard.domain.data.Notification;
import com.shipyard.domain.data.NotificationStatus;
import com.shipyard.domain.data.Workweek;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NotificationBuilder {
    private Long id;
    private NotificationStatus status;
    private ContactPersonBuilder yardContact = new ContactPersonBuilder();
    private ContactPersonBuilder siteForeman = new ContactPersonBuilder();
    private String additionalInformation;
    private WorkweekBuilder workWeek = new WorkweekBuilder();
    private List<WorkEntryBuilder> workEntries = new ArrayList<>();
    private List<LogEntryBuilder> log = new ArrayList<>();

    public static NotificationBuilder from(Notification notification) {
        return new NotificationBuilder()
                .withId(notification.getId())
                .withStatus(notification.getStatus())
                .withYardContact(notification.getYardContact())
                .withSiteForeman(notification.getSiteForeman())
                .withAdditionalInformation(notification.getAdditionalInformation())
                .withWorkWeek(notification.getWorkWeek())
                .withWorkEntries(notification.getWorkEntries().stream().map(WorkEntryBuilder::from).collect(Collectors.toList()))
                .withLog(notification.getLog().stream().map(LogEntryBuilder::from).collect(Collectors.toList()));
    }

    /**
     * Surrogate key.
     */
    public NotificationBuilder withId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
        return this;
    }

    /**
     * The state of the notification.
     */
    public NotificationBuilder withStatus(NotificationStatus status) {
        Objects.requireNonNull(status);
        this.status = status;
        return this;
    }

    /**
     * The responsible person about this notification on the yard side.
     */
    public ContactPersonBuilder getYardContact() {
        return yardContact;
    }

    /**
     * The responsible person about this notification on the yard side.
     */
    public NotificationBuilder withYardContact(ContactPersonBuilder yardContact) {
        Objects.requireNonNull(yardContact);
        this.yardContact = yardContact;
        return this;
    }

    /**
     * The responsible person about this notification on the yard side.
     */
    public NotificationBuilder withYardContact(ContactPerson yardContact) {
        Objects.requireNonNull(yardContact);
        this.yardContact = ContactPersonBuilder.from(yardContact);
        return this;
    }

    /**
     * The site foreman who is on the premises supervising the work.
     */
    public ContactPersonBuilder getSiteForeman() {
        return siteForeman;
    }

    /**
     * The site foreman who is on the premises supervising the work.
     */
    public NotificationBuilder withSiteForeman(ContactPersonBuilder siteForeman) {
        Objects.requireNonNull(siteForeman);
        this.siteForeman = siteForeman;
        return this;
    }

    /**
     * The site foreman who is on the premises supervising the work.
     */
    public NotificationBuilder withSiteForeman(ContactPerson siteForeman) {
        Objects.requireNonNull(siteForeman);
        this.siteForeman = ContactPersonBuilder.from(siteForeman);
        return this;
    }

    /**
     * Comments and additional info.
     */
    public NotificationBuilder withAdditionalInformation(String additionalInformation) {
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
    public NotificationBuilder withWorkWeek(WorkweekBuilder workWeek) {
        Objects.requireNonNull(workWeek);
        this.workWeek = workWeek;
        return this;
    }

    /**
     * The working week this notification is about.
     */
    public NotificationBuilder withWorkWeek(Workweek workWeek) {
        Objects.requireNonNull(workWeek);
        this.workWeek = WorkweekBuilder.from(workWeek);
        return this;
    }

    /**
     * Entries about each worker, his/her location and energy requirements.
     */
    public List<WorkEntryBuilder> getWorkEntries() {
        return workEntries;
    }

    /**
     * Entries about each worker, his/her location and energy requirements.
     */
    public NotificationBuilder withWorkEntries(List<WorkEntryBuilder> workEntries) {
        Objects.requireNonNull(workEntries);
        this.workEntries = workEntries;
        return this;
    }

    /**
     * All events in the history of the notification.
     */
    public List<LogEntryBuilder> getLog() {
        return log;
    }

    /**
     * All events in the history of the notification.
     */
    public NotificationBuilder withLog(List<LogEntryBuilder> log) {
        Objects.requireNonNull(log);
        this.log = log;
        return this;
    }

    public Notification build() {
        return new Notification(id, status, yardContact.build(), siteForeman.build(), additionalInformation, workWeek.build(), workEntries.stream().map(WorkEntryBuilder::build).collect(Collectors.toList()), log.stream().map(LogEntryBuilder::build).collect(Collectors.toList()));
    }
}
