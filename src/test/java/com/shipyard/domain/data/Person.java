package com.shipyard.domain.data;

import java.util.Objects;

/**
 * A person's basic information (currently only name).
 */
public class Person {
    private final Long id;
    private final String firstName;
    private final String lastName;

    public Person(
            Long id,
            String firstName,
            String lastName) {
        Objects.requireNonNull(id, "Field 'id' of class Person cannot be null.");
        Objects.requireNonNull(firstName, "Field 'firstName' of class Person cannot be null.");
        Objects.requireNonNull(lastName, "Field 'lastName' of class Person cannot be null.");
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Surrogate key.
     */
    public Long getId() {
        return id;
    }

    /**
     * First name, for example "Ville".
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Last name, for example "Peurala".
     */
    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Person{"
                + "id='" + getId() + "'"
                + ", " + "firstName='" + getFirstName() + "'"
                + ", " + "lastName='" + getLastName() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person other = (Person) o;

        if (!getId().equals(other.getId())) return false;
        if (!getFirstName().equals(other.getFirstName())) return false;
        if (!getLastName().equals(other.getLastName())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getId().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        return result;
    }
}
