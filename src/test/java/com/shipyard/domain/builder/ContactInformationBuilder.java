package com.shipyard.domain.builder;

import com.shipyard.domain.data.ContactInformation;

import java.util.Objects;

public class ContactInformationBuilder {
    private String email;
    private String phoneNumber;

    public static ContactInformationBuilder from(ContactInformation contactInformation) {
        return new ContactInformationBuilder()
                .withEmail(contactInformation.getEmail())
                .withPhoneNumber(contactInformation.getPhoneNumber());
    }

    /**
     * Email address, for example "ville.peurala@mail.com".
     */
    public ContactInformationBuilder withEmail(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }

    /**
     * Phone number, for example "050 - 352 7878".
     */
    public ContactInformationBuilder withPhoneNumber(String phoneNumber) {
        Objects.requireNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ContactInformation build() {
        return new ContactInformation(email, phoneNumber);
    }
}
