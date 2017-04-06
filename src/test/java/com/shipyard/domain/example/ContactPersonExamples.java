package com.shipyard.domain.example;

import com.shipyard.domain.builder.ContactPersonBuilder;

public class ContactPersonExamples {
    public static ContactPersonBuilder villePeuralaWithContactInformation() {
        return new ContactPersonBuilder()
                .withFirstName("Ville")
                .withLastName("Peurala")
                .withCompany(CompanyExamples.sTXGroup())
                .withContactInformation(ContactInformationExamples.contactInformationForVillePeurala());
    }

    public static ContactPersonBuilder teroPackalenWithContactInformation() {
        return new ContactPersonBuilder()
                .withFirstName("Tero")
                .withLastName("Packalen")
                .withCompany(CompanyExamples.maersk())
                .withContactInformation(ContactInformationExamples.contactInformationForTeroPackalen());
    }
}
