package com.shipyard.domain.builder;

import com.shipyard.domain.data.Company;
import com.shipyard.domain.data.ContactInformation;
import com.shipyard.domain.data.User;

import java.util.Objects;

public class UserBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private CompanyBuilder company = new CompanyBuilder();
    private ContactInformationBuilder contactInformation = new ContactInformationBuilder();
    private Boolean confirmed;
    private String token;
    private String password;

    public static UserBuilder from(User user) {
        return new UserBuilder()
                .withId(user.getId())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withCompany(user.getCompany())
                .withContactInformation(user.getContactInformation())
                .withConfirmed(user.getConfirmed())
                .withToken(user.getToken())
                .withPassword(user.getPassword());
    }

    /**
     * Surrogate key.
     */
    public UserBuilder withId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
        return this;
    }

    /**
     * First name, for example "Ville".
     */
    public UserBuilder withFirstName(String firstName) {
        Objects.requireNonNull(firstName);
        this.firstName = firstName;
        return this;
    }

    /**
     * Last name, for example "Peurala".
     */
    public UserBuilder withLastName(String lastName) {
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
    public UserBuilder withCompany(CompanyBuilder company) {
        Objects.requireNonNull(company);
        this.company = company;
        return this;
    }

    /**
     * The company the person works for.
     */
    public UserBuilder withCompany(Company company) {
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
    public UserBuilder withContactInformation(ContactInformationBuilder contactInformation) {
        Objects.requireNonNull(contactInformation);
        this.contactInformation = contactInformation;
        return this;
    }

    /**
     *
     */
    public UserBuilder withContactInformation(ContactInformation contactInformation) {
        Objects.requireNonNull(contactInformation);
        this.contactInformation = ContactInformationBuilder.from(contactInformation);
        return this;
    }

    /**
     * Whether this user is activated by confirming the email address.
     */
    public UserBuilder withConfirmed(Boolean confirmed) {
        Objects.requireNonNull(confirmed);
        this.confirmed = confirmed;
        return this;
    }

    /**
     * An identification token for the user.
     */
    public UserBuilder withToken(String token) {
        Objects.requireNonNull(token);
        this.token = token;
        return this;
    }

    /**
     * A one-way hash of the user's password.
     */
    public UserBuilder withPassword(String password) {
        Objects.requireNonNull(password);
        this.password = password;
        return this;
    }

    public User build() {
        return new User(id, firstName, lastName, company.build(), contactInformation.build(), confirmed, token, password);
    }
}
