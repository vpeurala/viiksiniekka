package com.shipyard.domain.data;

import java.util.Objects;

/**
 * A person with a company (the person's employer).
 */
public class EmployedPerson extends Person {
    private final Company company;

    public EmployedPerson(
            Long id,
            String firstName,
            String lastName,
            Company company) {
        super(id, firstName, lastName);
        Objects.requireNonNull(company, "Field 'company' of class EmployedPerson cannot be null.");
        this.company = company;
    }

    /**
     * The company the person works for.
     */
    public Company getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return "EmployedPerson{"
                + "id='" + getId() + "'"
                + ", " + "firstName='" + getFirstName() + "'"
                + ", " + "lastName='" + getLastName() + "'"
                + ", " + "company='" + getCompany() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployedPerson other = (EmployedPerson) o;

        if (!getId().equals(other.getId())) return false;
        if (!getFirstName().equals(other.getFirstName())) return false;
        if (!getLastName().equals(other.getLastName())) return false;
        if (!getCompany().equals(other.getCompany())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getId().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getCompany().hashCode();
        return result;
    }
}
