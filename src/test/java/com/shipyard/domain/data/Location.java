package com.shipyard.domain.data;

import java.util.Objects;
import java.util.Optional;

/**
 * A location always includes a building. It can also optionally include a ship and a ship area.
 */
public class Location {
    private final Building building;
    private final Optional<Ship> ship;
    private final Optional<ShipArea> shipArea;

    public Location(
            Building building,
            Optional<Ship> ship,
            Optional<ShipArea> shipArea) {
        Objects.requireNonNull(building, "Field 'building' of class Location cannot be null.");
        Objects.requireNonNull(ship, "Field 'ship' of class Location cannot be null.");
        Objects.requireNonNull(shipArea, "Field 'shipArea' of class Location cannot be null.");
        this.building = building;
        this.ship = ship;
        this.shipArea = shipArea;
    }

    /**
     * The building, for example "Building 68".
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * The ship, for example "Ship 4".
     */
    public Optional<Ship> getShip() {
        return ship;
    }

    /**
     * The ship area, for example "75 (Gallows)".
     */
    public Optional<ShipArea> getShipArea() {
        return shipArea;
    }

    @Override
    public String toString() {
        return "Location{"
                + "building='" + getBuilding() + "'"
                + ", " + "ship='" + getShip() + "'"
                + ", " + "shipArea='" + getShipArea() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location other = (Location) o;

        if (!getBuilding().equals(other.getBuilding())) return false;
        if (!getShip().equals(other.getShip())) return false;
        if (!getShipArea().equals(other.getShipArea())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getBuilding().hashCode();
        result = 31 * result + getShip().hashCode();
        result = 31 * result + getShipArea().hashCode();
        return result;
    }
}
