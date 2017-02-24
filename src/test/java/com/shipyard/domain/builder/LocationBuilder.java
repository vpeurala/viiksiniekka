package com.shipyard.domain.builder;

import com.shipyard.domain.data.Building;
import com.shipyard.domain.data.Location;
import com.shipyard.domain.data.Ship;
import com.shipyard.domain.data.ShipArea;

import java.util.Objects;
import java.util.Optional;

public class LocationBuilder {
    private BuildingBuilder building = new BuildingBuilder();
    private Optional<ShipBuilder> ship = Optional.empty();
    private Optional<ShipAreaBuilder> shipArea = Optional.empty();

    public static LocationBuilder from(Location location) {
        return new LocationBuilder()
                .withBuilding(location.getBuilding())
                .withShipOpt(location.getShip())
                .withShipAreaOpt(location.getShipArea());
    }

    /**
     * The building, for example "Building 68".
     */
    public BuildingBuilder getBuilding() {
        return building;
    }

    /**
     * The building, for example "Building 68".
     */
    public LocationBuilder withBuilding(BuildingBuilder building) {
        Objects.requireNonNull(building);
        this.building = building;
        return this;
    }

    /**
     * The building, for example "Building 68".
     */
    public LocationBuilder withBuilding(Building building) {
        Objects.requireNonNull(building);
        this.building = BuildingBuilder.from(building);
        return this;
    }

    /**
     * The ship, for example "Ship 4".
     */
    public Optional<ShipBuilder> getShip() {
        return ship;
    }

    /**
     * The ship, for example "Ship 4".
     */
    public LocationBuilder withShip(ShipBuilder ship) {
        Objects.requireNonNull(ship);
        this.ship = Optional.of(ship);
        return this;
    }

    /**
     * The ship, for example "Ship 4".
     */
    public LocationBuilder withShip(Ship ship) {
        Objects.requireNonNull(ship);
        this.ship = Optional.of(ShipBuilder.from(ship));
        return this;
    }

    /**
     * The ship, for example "Ship 4".
     */
    private LocationBuilder withShipOpt(Optional<Ship> ship) {
        Objects.requireNonNull(ship);
        this.ship = ship.map(ShipBuilder::from);
        return this;
    }

    /**
     * The ship, for example "Ship 4".
     */
    public LocationBuilder withoutShip() {
        this.ship = Optional.empty();
        return this;
    }

    /**
     * The ship area, for example "75 (Gallows)".
     */
    public Optional<ShipAreaBuilder> getShipArea() {
        return shipArea;
    }

    /**
     * The ship area, for example "75 (Gallows)".
     */
    public LocationBuilder withShipArea(ShipAreaBuilder shipArea) {
        Objects.requireNonNull(shipArea);
        this.shipArea = Optional.of(shipArea);
        return this;
    }

    /**
     * The ship area, for example "75 (Gallows)".
     */
    public LocationBuilder withShipArea(ShipArea shipArea) {
        Objects.requireNonNull(shipArea);
        this.shipArea = Optional.of(ShipAreaBuilder.from(shipArea));
        return this;
    }

    /**
     * The ship area, for example "75 (Gallows)".
     */
    private LocationBuilder withShipAreaOpt(Optional<ShipArea> shipArea) {
        Objects.requireNonNull(shipArea);
        this.shipArea = shipArea.map(ShipAreaBuilder::from);
        return this;
    }

    /**
     * The ship area, for example "75 (Gallows)".
     */
    public LocationBuilder withoutShipArea() {
        this.shipArea = Optional.empty();
        return this;
    }

    public Location build() {
        return new Location(building.build(), ship.map(ShipBuilder::build), shipArea.map(ShipAreaBuilder::build));
    }
}
