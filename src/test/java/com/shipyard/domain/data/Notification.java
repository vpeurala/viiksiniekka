package com.shipyard.domain.data;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A notification about workers and their work hours.
 */
public class Notification {
    private final Long id;
    private final NotificationStatus status;
    private final ContactPerson yardContact;
    private final ContactPerson siteForeman;
    private final String additionalInformation;
    private final Workweek workWeek;
    private final List<WorkEntry> workEntries;
    private final List<LogEntry> log;

    public Notification(
            Long id,
            NotificationStatus status,
            ContactPerson yardContact,
            ContactPerson siteForeman,
            String additionalInformation,
            Workweek workWeek,
            List<WorkEntry> workEntries,
            List<LogEntry> log) {
        Objects.requireNonNull(id, "Field 'id' of class Notification cannot be null.");
        Objects.requireNonNull(status, "Field 'status' of class Notification cannot be null.");
        Objects.requireNonNull(yardContact, "Field 'yardContact' of class Notification cannot be null.");
        Objects.requireNonNull(siteForeman, "Field 'siteForeman' of class Notification cannot be null.");
        Objects.requireNonNull(additionalInformation, "Field 'additionalInformation' of class Notification cannot be null.");
        Objects.requireNonNull(workWeek, "Field 'workWeek' of class Notification cannot be null.");
        Objects.requireNonNull(workEntries, "Field 'workEntries' of class Notification cannot be null.");
        Objects.requireNonNull(log, "Field 'log' of class Notification cannot be null.");
        this.id = id;
        this.status = status;
        this.yardContact = yardContact;
        this.siteForeman = siteForeman;
        this.additionalInformation = additionalInformation;
        this.workWeek = workWeek;
        this.workEntries = Collections.unmodifiableList(workEntries);
        this.log = Collections.unmodifiableList(log);
    }

    /**
     * Surrogate key.
     */
    public Long getId() {
        return id;
    }

    /**
     * The state of the notification.
     */
    public NotificationStatus getStatus() {
        return status;
    }

    /**
     * The responsible person about this notification on the yard side.
     */
    public ContactPerson getYardContact() {
        return yardContact;
    }

    /**
     * The site foreman who is on the premises supervising the work.
     */
    public ContactPerson getSiteForeman() {
        return siteForeman;
    }

    /**
     * Comments and additional info.
     */
    public String getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * The working week this notification is about.
     */
    public Workweek getWorkWeek() {
        return workWeek;
    }

    /**
     * Entries about each worker, his/her location and energy requirements.
     */
    public List<WorkEntry> getWorkEntries() {
        return workEntries;
    }

    /**
     * All events in the history of the notification.
     */
    public List<LogEntry> getLog() {
        return log;
    }

    @Override
    public String toString() {
        return "Notification{"
                + "id='" + getId() + "'"
                + ", " + "status='" + getStatus() + "'"
                + ", " + "yardContact='" + getYardContact() + "'"
                + ", " + "siteForeman='" + getSiteForeman() + "'"
                + ", " + "additionalInformation='" + getAdditionalInformation() + "'"
                + ", " + "workWeek='" + getWorkWeek() + "'"
                + ", " + "workEntries='" + getWorkEntries() + "'"
                + ", " + "log='" + getLog() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification other = (Notification) o;

        if (!getId().equals(other.getId())) return false;
        if (!getStatus().equals(other.getStatus())) return false;
        if (!getYardContact().equals(other.getYardContact())) return false;
        if (!getSiteForeman().equals(other.getSiteForeman())) return false;
        if (!getAdditionalInformation().equals(other.getAdditionalInformation())) return false;
        if (!getWorkWeek().equals(other.getWorkWeek())) return false;
        if (!getWorkEntries().equals(other.getWorkEntries())) return false;
        if (!getLog().equals(other.getLog())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getId().hashCode();
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + getYardContact().hashCode();
        result = 31 * result + getSiteForeman().hashCode();
        result = 31 * result + getAdditionalInformation().hashCode();
        result = 31 * result + getWorkWeek().hashCode();
        result = 31 * result + getWorkEntries().hashCode();
        result = 31 * result + getLog().hashCode();
        return result;
    }
}
