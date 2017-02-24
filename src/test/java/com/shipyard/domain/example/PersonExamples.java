package com.shipyard.domain.example;

import com.shipyard.domain.builder.PersonBuilder;

public class PersonExamples {
    public static PersonBuilder villePeurala() {
        return new PersonBuilder()
                .withFirstName("Ville")
                .withLastName("Peurala");
    }

    public static PersonBuilder teroPackalen() {
        return new PersonBuilder()
                .withFirstName("Tero")
                .withLastName("Packalen");
    }
}
