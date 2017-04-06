package com.shipyard.domain.data;

/**
 * The state a notification is in.
 */
public enum NotificationStatus {
    APPROVED("Approved"), DRAFT("Draft"), REJECTED("Rejected"), WAITING_FOR_YARD_CONTACT("Waiting for yard contact"), WAITING_FOR_PRODUCTION_MANAGER("Waiting for production manager");

    private final String value;

    private NotificationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static NotificationStatus forValue(String input) {
        switch (input) {
            case "Approved":
                return APPROVED;
            case "Draft":
                return DRAFT;
            case "Rejected":
                return REJECTED;
            case "Waiting for yard contact":
                return WAITING_FOR_YARD_CONTACT;
            case "Waiting for production manager":
                return WAITING_FOR_PRODUCTION_MANAGER;
            default:
                throw new IllegalArgumentException("No NotificationStatus value for input '" + input + "'.");
        }
    }
}
