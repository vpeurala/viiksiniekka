package com.shipyard.domain.builder;

import com.shipyard.domain.data.Company;
import com.shipyard.domain.data.Worker;

import java.util.Objects;

public class WorkerBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private CompanyBuilder company = new CompanyBuilder();
    private String keyCode;

    public static WorkerBuilder from(Worker worker) {
        return new WorkerBuilder()
                .withId(worker.getId())
                .withFirstName(worker.getFirstName())
                .withLastName(worker.getLastName())
                .withCompany(worker.getCompany())
                .withKeyCode(worker.getKeyCode());
    }

    /**
     * Surrogate key.
     */
    public WorkerBuilder withId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
        return this;
    }

    /**
     * First name, for example "Ville".
     */
    public WorkerBuilder withFirstName(String firstName) {
        Objects.requireNonNull(firstName);
        this.firstName = firstName;
        return this;
    }

    /**
     * Last name, for example "Peurala".
     */
    public WorkerBuilder withLastName(String lastName) {
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
    public WorkerBuilder withCompany(CompanyBuilder company) {
        Objects.requireNonNull(company);
        this.company = company;
        return this;
    }

    /**
     * The company the person works for.
     */
    public WorkerBuilder withCompany(Company company) {
        Objects.requireNonNull(company);
        this.company = CompanyBuilder.from(company);
        return this;
    }

    /**
     * A worker's unique identifying code, for example "4060".
     */
    public WorkerBuilder withKeyCode(String keyCode) {
        Objects.requireNonNull(keyCode);
        this.keyCode = keyCode;
        return this;
    }

    public Worker build() {
        return new Worker(id, firstName, lastName, company.build(), keyCode);
    }
}
