package com.shipyard.domain.example;

import com.shipyard.domain.builder.WorkerBuilder;

public class WorkerExamples {
    public static WorkerBuilder jurijAndrejev() {
        return new WorkerBuilder()
                .withFirstName("Jurij")
                .withLastName("Andrejev")
                .withCompany(CompanyExamples.ablemans())
                .withKeyCode("4060");
    }

    public static WorkerBuilder genadijBogira() {
        return new WorkerBuilder()
                .withFirstName("Genadij")
                .withLastName("Bogira")
                .withCompany(CompanyExamples.maersk())
                .withKeyCode("2332");
    }
}
