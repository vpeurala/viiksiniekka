package com.shipyard.domain.data;

import java.util.Objects;

/**
 * A person with a worker key code.
 */
public class Worker extends EmployedPerson {
    private final String keyCode;

    public Worker(
            Long id,
            String firstName,
            String lastName,
            Company company,
            String keyCode) {
        super(id, firstName, lastName, company);
        Objects.requireNonNull(keyCode, "Field 'keyCode' of class Worker cannot be null.");
        this.keyCode = keyCode;
    }

    /**
     * A worker's unique identifying code, for example "4060".
     */
    public String getKeyCode() {
        return keyCode;
    }

    @Override
    public String toString() {
        return "Worker{"
                + "id='" + getId() + "'"
                + ", " + "firstName='" + getFirstName() + "'"
                + ", " + "lastName='" + getLastName() + "'"
                + ", " + "company='" + getCompany() + "'"
                + ", " + "keyCode='" + getKeyCode() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Worker other = (Worker) o;

        if (!getId().equals(other.getId())) return false;
        if (!getFirstName().equals(other.getFirstName())) return false;
        if (!getLastName().equals(other.getLastName())) return false;
        if (!getCompany().equals(other.getCompany())) return false;
        if (!getKeyCode().equals(other.getKeyCode())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getId().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getCompany().hashCode();
        result = 31 * result + getKeyCode().hashCode();
        return result;
    }
}
