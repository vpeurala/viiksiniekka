package com.shipyard.domain.example;

import com.shipyard.domain.builder.UserBuilder;

public class UserExamples {
    public static UserBuilder villePeuralaGmailCom() {
        return new UserBuilder()
                .withFirstName("Ville")
                .withLastName("Peurala")
                .withCompany(CompanyExamples.sTXGroup())
                .withContactInformation(ContactInformationExamples.contactInformationForVillePeurala())
                .withConfirmed(true)
                .withToken("76696c6c652e70657572616c614077756e646572646f672e6670");
    }

    public static UserBuilder teroPackalenYardCom() {
        return new UserBuilder()
                .withFirstName("Tero")
                .withLastName("Packalen")
                .withCompany(CompanyExamples.maersk())
                .withContactInformation(ContactInformationExamples.contactInformationForTeroPackalen())
                .withConfirmed(true)
                .withToken("76696c6c652e70657572616c614077756e646572646f672e6671");
    }
}
