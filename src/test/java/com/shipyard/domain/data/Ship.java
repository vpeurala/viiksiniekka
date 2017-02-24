package com.shipyard.domain.data;

import java.util.Objects;

/**
 *
 */
public class Ship {
    private final Long id;
    private final String code;
    private final String description;

    public Ship(
            Long id,
            String code,
            String description) {
        Objects.requireNonNull(id, "Field 'id' of class Ship cannot be null.");
        Objects.requireNonNull(code, "Field 'code' of class Ship cannot be null.");
        Objects.requireNonNull(description, "Field 'description' of class Ship cannot be null.");
        this.id = id;
        this.code = code;
        this.description = description;
    }

    /**
     * Surrogate key.
     */
    public Long getId() {
        return id;
    }

    /**
     * The ship code, for example "N 2".
     */
    public String getCode() {
        return code;
    }

    /**
     * The ship description, for example "Ship 2".
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Ship{"
                + "id='" + getId() + "'"
                + ", " + "code='" + getCode() + "'"
                + ", " + "description='" + getDescription() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship other = (Ship) o;

        if (!getId().equals(other.getId())) return false;
        if (!getCode().equals(other.getCode())) return false;
        if (!getDescription().equals(other.getDescription())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getId().hashCode();
        result = 31 * result + getCode().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }
}
