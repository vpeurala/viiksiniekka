package com.shipyard.domain.example;

import com.shipyard.domain.builder.ContactInformationBuilder;

public class ContactInformationExamples {
    public static ContactInformationBuilder contactInformationForVillePeurala() {
        return new ContactInformationBuilder()
                .withEmail("ville.peurala@mail.com")
                .withPhoneNumber("050 - 352 7878");
    }

    public static ContactInformationBuilder contactInformationForTeroPackalen() {
        return new ContactInformationBuilder()
                .withEmail("tero.packalen@yard.com")
                .withPhoneNumber("040 - 568 3313");
    }
}
