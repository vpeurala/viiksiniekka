package com.shipyard.domain.data;

import java.util.Objects;

/**
 * A user of the application.
 */
public class User extends ContactPerson {
    private final Boolean confirmed;
    private final String token;
    private final String password;

    public User(
            Long id,
            String firstName,
            String lastName,
            Company company,
            ContactInformation contactInformation,
            Boolean confirmed,
            String token,
            String password) {
        super(id, firstName, lastName, company, contactInformation);
        Objects.requireNonNull(confirmed, "Field 'confirmed' of class User cannot be null.");
        Objects.requireNonNull(token, "Field 'token' of class User cannot be null.");
        Objects.requireNonNull(password, "Field 'password' of class User cannot be null.");
        this.confirmed = confirmed;
        this.token = token;
        this.password = password;
    }

    /**
     * Whether this user is activated by confirming the email address.
     */
    public Boolean getConfirmed() {
        return confirmed;
    }

    /**
     * An identification token for the user.
     */
    public String getToken() {
        return token;
    }

    /**
     * A one-way hash of the user's password.
     */
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{"
                + "id='" + getId() + "'"
                + ", " + "firstName='" + getFirstName() + "'"
                + ", " + "lastName='" + getLastName() + "'"
                + ", " + "company='" + getCompany() + "'"
                + ", " + "contactInformation='" + getContactInformation() + "'"
                + ", " + "confirmed='" + getConfirmed() + "'"
                + ", " + "token='" + getToken() + "'"
                + ", " + "password='" + getPassword() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User other = (User) o;

        if (!getId().equals(other.getId())) return false;
        if (!getFirstName().equals(other.getFirstName())) return false;
        if (!getLastName().equals(other.getLastName())) return false;
        if (!getCompany().equals(other.getCompany())) return false;
        if (!getContactInformation().equals(other.getContactInformation())) return false;
        if (!getConfirmed().equals(other.getConfirmed())) return false;
        if (!getToken().equals(other.getToken())) return false;
        if (!getPassword().equals(other.getPassword())) return false;
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
        result = 31 * result + getConfirmed().hashCode();
        result = 31 * result + getToken().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }
}
