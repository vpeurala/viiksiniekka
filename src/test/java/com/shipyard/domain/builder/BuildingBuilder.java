package com.shipyard.domain.builder;

import com.shipyard.domain.data.Building;

import java.util.Objects;

public class BuildingBuilder {
    private Long id;
    private String code;
    private String description;

    public static BuildingBuilder from(Building building) {
        return new BuildingBuilder()
                .withId(building.getId())
                .withCode(building.getCode())
                .withDescription(building.getDescription());
    }

    /**
     * Surrogate key.
     */
    public BuildingBuilder withId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
        return this;
    }

    /**
     * The building code, for example "B 43".
     */
    public BuildingBuilder withCode(String code) {
        Objects.requireNonNull(code);
        this.code = code;
        return this;
    }

    /**
     * The building description, for example "Assembly shed".
     */
    public BuildingBuilder withDescription(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }

    public Building build() {
        return new Building(id, code, description);
    }
}
