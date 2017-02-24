package com.shipyard.domain.builder;

import com.shipyard.domain.data.Ship;

import java.util.Objects;

public class ShipBuilder {
    private Long id;
    private String code;
    private String description;

    public static ShipBuilder from(Ship ship) {
        return new ShipBuilder()
                .withId(ship.getId())
                .withCode(ship.getCode())
                .withDescription(ship.getDescription());
    }

    /**
     * Surrogate key.
     */
    public ShipBuilder withId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
        return this;
    }

    /**
     * The ship code, for example "N 2".
     */
    public ShipBuilder withCode(String code) {
        Objects.requireNonNull(code);
        this.code = code;
        return this;
    }

    /**
     * The ship description, for example "Ship 2".
     */
    public ShipBuilder withDescription(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }

    public Ship build() {
        return new Ship(id, code, description);
    }
}
