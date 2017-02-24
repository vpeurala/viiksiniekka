package com.shipyard.domain.data;

import java.util.Objects;

/**
 * A person with contact information.
 */
public class ContactPerson extends EmployedPerson {
    private final ContactInformation contactInformation;

    public ContactPerson(
            Long id,
            String firstName,
            String lastName,
            Company company,
            ContactInformation contactInformation) {
        super(id, firstName, lastName, company);
        Objects.requireNonNull(contactInformation, "Field 'contactInformation' of class ContactPerson cannot be null.");
        this.contactInformation = contactInformation;
    }

    /**
     *
     */
    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    @Override
    public String toString() {
        return "ContactPerson{"
                + "id='" + getId() + "'"
                + ", " + "firstName='" + getFirstName() + "'"
                + ", " + "lastName='" + getLastName() + "'"
                + ", " + "company='" + getCompany() + "'"
                + ", " + "contactInformation='" + getContactInformation() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactPerson other = (ContactPerson) o;

        if (!getId().equals(other.getId())) return false;
        if (!getFirstName().equals(other.getFirstName())) return false;
        if (!getLastName().equals(other.getLastName())) return false;
        if (!getCompany().equals(other.getCompany())) return false;
        if (!getContactInformation().equals(other.getContactInformation())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getId().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getCompany().hashCode();
        result = 31 * result + getContactInformation().hashCode();
        return result;
    }
}
