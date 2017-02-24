package com.shipyard.domain.example;

import com.shipyard.domain.builder.CompanyBuilder;

public class CompanyExamples {
    public static CompanyBuilder ablemans() {
        return new CompanyBuilder()
                .withName("Ablemans");
    }

    public static CompanyBuilder maersk() {
        return new CompanyBuilder()
                .withName("Maersk");
    }

    public static CompanyBuilder sTXGroup() {
        return new CompanyBuilder()
                .withName("STX Group");
    }
}
