package com.shipyard.domain.data;

/**
 * The task which a worker is performing.
 */
public enum Occupation {
    FITTER("Fitter"), WELDER("Welder"), PIPE_FITTER("Pipe fitter"), PROJECT_MANAGER("Project manager"), OTHER("Other");

    private final String value;

    private Occupation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Occupation forValue(String input) {
        switch (input) {
            case "Fitter":
                return FITTER;
            case "Welder":
                return WELDER;
            case "Pipe fitter":
                return PIPE_FITTER;
            case "Project manager":
                return PROJECT_MANAGER;
            case "Other":
                return OTHER;
            default:
                throw new IllegalArgumentException("No Occupation value for input '" + input + "'.");
        }
    }
}
