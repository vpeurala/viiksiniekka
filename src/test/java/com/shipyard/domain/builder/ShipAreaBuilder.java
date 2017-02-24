package com.shipyard.domain.builder;

import com.shipyard.domain.data.ShipArea;

import java.util.Objects;

public class ShipAreaBuilder {
    private Long id;
    private String code;
    private String description;

    public static ShipAreaBuilder from(ShipArea shipArea) {
        return new ShipAreaBuilder()
                .withId(shipArea.getId())
                .withCode(shipArea.getCode())
                .withDescription(shipArea.getDescription());
    }

    /**
     * Surrogate key.
     */
    public ShipAreaBuilder withId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
        return this;
    }

    /**
     * The ship area code, for example "56".
     */
    public ShipAreaBuilder withCode(String code) {
        Objects.requireNonNull(code);
        this.code = code;
        return this;
    }

    /**
     * The ship area description, for example "Cabins".
     */
    public ShipAreaBuilder withDescription(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }

    public ShipArea build() {
        return new ShipArea(id, code, description);
    }
}
