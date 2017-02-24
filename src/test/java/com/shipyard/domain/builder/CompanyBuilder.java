package com.shipyard.domain.builder;

import com.shipyard.domain.data.Company;

import java.util.Objects;

public class CompanyBuilder {
    private Long id;
    private String name;

    public static CompanyBuilder from(Company company) {
        return new CompanyBuilder()
                .withId(company.getId())
                .withName(company.getName());
    }

    /**
     * Surrogate key.
     */
    public CompanyBuilder withId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
        return this;
    }

    /**
     * The name of the company.
     */
    public CompanyBuilder withName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }

    public Company build() {
        return new Company(id, name);
    }
}
