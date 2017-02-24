package com.shipyard.domain.builder;

import com.shipyard.domain.data.Company;
import com.shipyard.domain.data.ContactInformation;
import com.shipyard.domain.data.ContactPerson;

import java.util.Objects;

public class ContactPersonBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private CompanyBuilder company = new CompanyBuilder();
    private ContactInformationBuilder contactInformation = new ContactInformationBuilder();

    public static ContactPersonBuilder from(ContactPerson contactPerson) {
        return new ContactPersonBuilder()
                .withId(contactPerson.getId())
                .withFirstName(contactPerson.getFirstName())
                .withLastName(contactPerson.getLastName())
                .withCompany(contactPerson.getCompany())
                .withContactInformation(contactPerson.getContactInformation());
    }

    /**
     * Surrogate key.
     */
    public ContactPersonBuilder withId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
        return this;
    }

    /**
     * First name, for example "Ville".
     */
    public ContactPersonBuilder withFirstName(String firstName) {
        Objects.requireNonNull(firstName);
        this.firstName = firstName;
        return this;
    }

    /**
     * Last name, for example "Peurala".
     */
    public ContactPersonBuilder withLastName(String lastName) {
        Objects.requireNonNull(lastName);
        this.lastName = lastName;
        return this;
    }

    /**
     * The company the person works for.
     */
    public CompanyBuilder getCompany() {
        return company;
    }

    /**
     * The company the person works for.
     */
    public ContactPersonBuilder withCompany(CompanyBuilder company) {
        Objects.requireNonNull(company);
        this.company = company;
        return this;
    }

    /**
     * The company the person works for.
     */
    public ContactPersonBuilder withCompany(Company company) {
        Objects.requireNonNull(company);
        this.company = CompanyBuilder.from(company);
        return this;
    }

    /**
     *
     */
    public ContactInformationBuilder getContactInformation() {
        return contactInformation;
    }

    /**
     *
     */
    public ContactPersonBuilder withContactInformation(ContactInformationBuilder contactInformation) {
        Objects.requireNonNull(contactInformation);
        this.contactInformation = contactInformation;
        return this;
    }

    /**
     *
     */
    public ContactPersonBuilder withContactInformation(ContactInformation contactInformation) {
        Objects.requireNonNull(contactInformation);
        this.contactInformation = ContactInformationBuilder.from(contactInformation);
        return this;
    }

    public ContactPerson build() {
        return new ContactPerson(id, firstName, lastName, company.build(), contactInformation.build());
    }
}
