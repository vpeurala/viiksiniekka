package com.shipyard.domain.builder;

import com.shipyard.domain.data.LogEntry;
import com.shipyard.domain.data.NotificationAction;
import com.shipyard.domain.data.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class LogEntryBuilder {
    private Long id;
    private NotificationAction action;
    private LocalDateTime createdAt;
    private UserBuilder createdBy = new UserBuilder();

    public static LogEntryBuilder from(LogEntry logEntry) {
        return new LogEntryBuilder()
                .withId(logEntry.getId())
                .withAction(logEntry.getAction())
                .withCreatedAt(logEntry.getCreatedAt())
                .withCreatedBy(logEntry.getCreatedBy());
    }

    /**
     * Surrogate key.
     */
    public LogEntryBuilder withId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
        return this;
    }

    /**
     *
     */
    public LogEntryBuilder withAction(NotificationAction action) {
        Objects.requireNonNull(action);
        this.action = action;
        return this;
    }

    /**
     *
     */
    public LogEntryBuilder withCreatedAt(LocalDateTime createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
        return this;
    }

    /**
     *
     */
    public UserBuilder getCreatedBy() {
        return createdBy;
    }

    /**
     *
     */
    public LogEntryBuilder withCreatedBy(UserBuilder createdBy) {
        Objects.requireNonNull(createdBy);
        this.createdBy = createdBy;
        return this;
    }

    /**
     *
     */
    public LogEntryBuilder withCreatedBy(User createdBy) {
        Objects.requireNonNull(createdBy);
        this.createdBy = UserBuilder.from(createdBy);
        return this;
    }

    public LogEntry build() {
        return new LogEntry(id, action, createdAt, createdBy.build());
    }
}
