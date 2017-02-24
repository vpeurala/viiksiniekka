package com.shipyard.domain.data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A logged event in a notification's history.
 */
public class LogEntry {
    private final Long id;
    private final NotificationAction action;
    private final LocalDateTime createdAt;
    private final User createdBy;

    public LogEntry(
            Long id,
            NotificationAction action,
            LocalDateTime createdAt,
            User createdBy) {
        Objects.requireNonNull(id, "Field 'id' of class LogEntry cannot be null.");
        Objects.requireNonNull(action, "Field 'action' of class LogEntry cannot be null.");
        Objects.requireNonNull(createdAt, "Field 'createdAt' of class LogEntry cannot be null.");
        Objects.requireNonNull(createdBy, "Field 'createdBy' of class LogEntry cannot be null.");
        this.id = id;
        this.action = action;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    /**
     * Surrogate key.
     */
    public Long getId() {
        return id;
    }

    /**
     *
     */
    public NotificationAction getAction() {
        return action;
    }

    /**
     *
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     *
     */
    public User getCreatedBy() {
        return createdBy;
    }

    @Override
    public String toString() {
        return "LogEntry{"
                + "id='" + getId() + "'"
                + ", " + "action='" + getAction() + "'"
                + ", " + "createdAt='" + getCreatedAt() + "'"
                + ", " + "createdBy='" + getCreatedBy() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEntry other = (LogEntry) o;

        if (!getId().equals(other.getId())) return false;
        if (!getAction().equals(other.getAction())) return false;
        if (!getCreatedAt().equals(other.getCreatedAt())) return false;
        if (!getCreatedBy().equals(other.getCreatedBy())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getId().hashCode();
        result = 31 * result + getAction().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        result = 31 * result + getCreatedBy().hashCode();
        return result;
    }
}
