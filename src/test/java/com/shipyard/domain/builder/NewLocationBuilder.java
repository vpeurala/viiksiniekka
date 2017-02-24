package com.shipyard.domain.builder;

import com.shipyard.domain.data.NewLocation;

import java.util.Objects;
import java.util.Optional;

public class NewLocationBuilder {
    private Long building;
    private Optional<Long> ship = Optional.empty();
    private Optional<Long> shipArea = Optional.empty();

    public static NewLocationBuilder from(NewLocation newLocation) {
        return new NewLocationBuilder()
                .withBuilding(newLocation.getBuilding())
                .withShipOpt(newLocation.getShip())
                .withShipAreaOpt(newLocation.getShipArea());
    }

    /**
     * The building, for example "Building 68".
     */
    public NewLocationBuilder withBuilding(Long building) {
        Objects.requireNonNull(building);
        this.building = building;
        return this;
    }

    /**
     * The ship, for example "Ship 4".
     */
    public NewLocationBuilder withShip(Long ship) {
        Objects.requireNonNull(ship);
        this.ship = Optional.of(ship);
        return this;
    }

    /**
     * The ship, for example "Ship 4".
     */
    private NewLocationBuilder withShipOpt(Optional<Long> ship) {
        Objects.requireNonNull(ship);
        this.ship = ship;
        return this;
    }

    /**
     * The ship, for example "Ship 4".
     */
    public NewLocationBuilder withoutShip() {
        this.ship = Optional.empty();
        return this;
    }

    /**
     * The ship area, for example "75 (Gallows)".
     */
    public NewLocationBuilder withShipArea(Long shipArea) {
        Objects.requireNonNull(shipArea);
        this.shipArea = Optional.of(shipArea);
        return this;
    }

    /**
     * The ship area, for example "75 (Gallows)".
     */
    private NewLocationBuilder withShipAreaOpt(Optional<Long> shipArea) {
        Objects.requireNonNull(shipArea);
        this.shipArea = shipArea;
        return this;
    }

    /**
     * The ship area, for example "75 (Gallows)".
     */
    public NewLocationBuilder withoutShipArea() {
        this.shipArea = Optional.empty();
        return this;
    }

    public NewLocation build() {
        return new NewLocation(building, ship, shipArea);
    }
}
