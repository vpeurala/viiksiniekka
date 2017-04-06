package com.shipyard.domain.data;

/**
 * The possible actions for a LogEntry.
 */
public enum NotificationAction {
    CREATE("Create"), SEND("Send"), APPROVE("Approve"), REJECT("Reject"), EDIT("Edit");

    private final String value;

    private NotificationAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static NotificationAction forValue(String input) {
        switch (input) {
            case "Create":
                return CREATE;
            case "Send":
                return SEND;
            case "Approve":
                return APPROVE;
            case "Reject":
                return REJECT;
            case "Edit":
                return EDIT;
            default:
                throw new IllegalArgumentException("No NotificationAction value for input '" + input + "'.");
        }
    }
}
