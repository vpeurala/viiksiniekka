package com.shipyard.domain.data;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A notification about workers and their work hours.
 */
public class NewNotification {
    private final NotificationStatus status;
    private final Long yardContact;
    private final Long siteForeman;
    private final String additionalInformation;
    private final Workweek workWeek;
    private final List<NewWorkEntry> workEntries;

    public NewNotification(
            NotificationStatus status,
            Long yardContact,
            Long siteForeman,
            String additionalInformation,
            Workweek workWeek,
            List<NewWorkEntry> workEntries) {
        Objects.requireNonNull(status, "Field 'status' of class NewNotification cannot be null.");
        Objects.requireNonNull(yardContact, "Field 'yardContact' of class NewNotification cannot be null.");
        Objects.requireNonNull(siteForeman, "Field 'siteForeman' of class NewNotification cannot be null.");
        Objects.requireNonNull(additionalInformation, "Field 'additionalInformation' of class NewNotification cannot be null.");
        Objects.requireNonNull(workWeek, "Field 'workWeek' of class NewNotification cannot be null.");
        Objects.requireNonNull(workEntries, "Field 'workEntries' of class NewNotification cannot be null.");
        this.status = status;
        this.yardContact = yardContact;
        this.siteForeman = siteForeman;
        this.additionalInformation = additionalInformation;
        this.workWeek = workWeek;
        this.workEntries = Collections.unmodifiableList(workEntries);
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
    public Long getYardContact() {
        return yardContact;
    }

    /**
     * The site foreman who is on the premises supervising the work.
     */
    public Long getSiteForeman() {
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
    public List<NewWorkEntry> getWorkEntries() {
        return workEntries;
    }

    @Override
    public String toString() {
        return "NewNotification{"
                + "status='" + getStatus() + "'"
                + ", " + "yardContact='" + getYardContact() + "'"
                + ", " + "siteForeman='" + getSiteForeman() + "'"
                + ", " + "additionalInformation='" + getAdditionalInformation() + "'"
                + ", " + "workWeek='" + getWorkWeek() + "'"
                + ", " + "workEntries='" + getWorkEntries() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewNotification other = (NewNotification) o;

        if (!getStatus().equals(other.getStatus())) return false;
        if (!getYardContact().equals(other.getYardContact())) return false;
        if (!getSiteForeman().equals(other.getSiteForeman())) return false;
        if (!getAdditionalInformation().equals(other.getAdditionalInformation())) return false;
        if (!getWorkWeek().equals(other.getWorkWeek())) return false;
        if (!getWorkEntries().equals(other.getWorkEntries())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + getYardContact().hashCode();
        result = 31 * result + getSiteForeman().hashCode();
        result = 31 * result + getAdditionalInformation().hashCode();
        result = 31 * result + getWorkWeek().hashCode();
        result = 31 * result + getWorkEntries().hashCode();
        return result;
    }
}
