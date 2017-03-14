package com.shipyard.domain.example;

import com.shipyard.domain.builder.ContactPersonBuilder;

public class ContactPersonExamples {
    public static ContactPersonBuilder villePeuralaWithContactInformation() {
        return new ContactPersonBuilder()
                .withFirstName("Ville")
                .withLastName("Peurala")
                .withContactInformation(ContactInformationExamples.contactInformationForVillePeurala());
    }

    public static ContactPersonBuilder teroPackalenWithContactInformation() {
        return new ContactPersonBuilder()
                .withFirstName("Tero")
                .withLastName("Packalen")
                .withContactInformation(ContactInformationExamples.contactInformationForTeroPackalen());
    }
}
