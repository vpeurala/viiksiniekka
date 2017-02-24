package com.shipyard.domain.data;

import java.util.Objects;

/**
 *
 */
public class ShipArea {
    private final Long id;
    private final String code;
    private final String description;

    public ShipArea(
            Long id,
            String code,
            String description) {
        Objects.requireNonNull(id, "Field 'id' of class ShipArea cannot be null.");
        Objects.requireNonNull(code, "Field 'code' of class ShipArea cannot be null.");
        Objects.requireNonNull(description, "Field 'description' of class ShipArea cannot be null.");
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
     * The ship area code, for example "56".
     */
    public String getCode() {
        return code;
    }

    /**
     * The ship area description, for example "Cabins".
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ShipArea{"
                + "id='" + getId() + "'"
                + ", " + "code='" + getCode() + "'"
                + ", " + "description='" + getDescription() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipArea other = (ShipArea) o;

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
