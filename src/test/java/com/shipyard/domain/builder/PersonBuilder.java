package com.shipyard.domain.builder;

import com.shipyard.domain.data.Person;

import java.util.Objects;

public class PersonBuilder {
    private Long id;
    private String firstName;
    private String lastName;

    public static PersonBuilder from(Person person) {
        return new PersonBuilder()
                .withId(person.getId())
                .withFirstName(person.getFirstName())
                .withLastName(person.getLastName());
    }

    /**
     * Surrogate key.
     */
    public PersonBuilder withId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
        return this;
    }

    /**
     * First name, for example "Ville".
     */
    public PersonBuilder withFirstName(String firstName) {
        Objects.requireNonNull(firstName);
        this.firstName = firstName;
        return this;
    }

    /**
     * Last name, for example "Peurala".
     */
    public PersonBuilder withLastName(String lastName) {
        Objects.requireNonNull(lastName);
        this.lastName = lastName;
        return this;
    }

    public Person build() {
        return new Person(id, firstName, lastName);
    }
}
