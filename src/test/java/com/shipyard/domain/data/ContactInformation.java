package com.shipyard.domain.data;

import java.util.Objects;

/**
 * Email and phone number.
 */
public class ContactInformation {
    private final String email;
    private final String phoneNumber;

    public ContactInformation(
            String email,
            String phoneNumber) {
        Objects.requireNonNull(email, "Field 'email' of class ContactInformation cannot be null.");
        Objects.requireNonNull(phoneNumber, "Field 'phoneNumber' of class ContactInformation cannot be null.");
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Email address, for example "ville.peurala@mail.com".
     */
    public String getEmail() {
        return email;
    }

    /**
     * Phone number, for example "050 - 352 7878".
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "ContactInformation{"
                + "email='" + getEmail() + "'"
                + ", " + "phoneNumber='" + getPhoneNumber() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactInformation other = (ContactInformation) o;

        if (!getEmail().equals(other.getEmail())) return false;
        if (!getPhoneNumber().equals(other.getPhoneNumber())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPhoneNumber().hashCode();
        return result;
    }
}
