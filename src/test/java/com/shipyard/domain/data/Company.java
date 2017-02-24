package com.shipyard.domain.data;

import java.util.Objects;

/**
 * A company.
 */
public class Company {
    private final Long id;
    private final String name;

    public Company(
            Long id,
            String name) {
        Objects.requireNonNull(id, "Field 'id' of class Company cannot be null.");
        Objects.requireNonNull(name, "Field 'name' of class Company cannot be null.");
        this.id = id;
        this.name = name;
    }

    /**
     * Surrogate key.
     */
    public Long getId() {
        return id;
    }

    /**
     * The name of the company.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Company{"
                + "id='" + getId() + "'"
                + ", " + "name='" + getName() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company other = (Company) o;

        if (!getId().equals(other.getId())) return false;
        if (!getName().equals(other.getName())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
