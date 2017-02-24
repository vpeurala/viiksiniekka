package com.shipyard.domain.example;

import com.shipyard.domain.builder.LocationBuilder;

public class LocationExamples {
    public static LocationBuilder atBuilding43() {
        return new LocationBuilder()
                .withBuilding(BuildingExamples.building43());
    }

    public static LocationBuilder atBuilding44Ship2() {
        return new LocationBuilder()
                .withBuilding(BuildingExamples.building44())
                .withShip(ShipExamples.ship2());
    }

    public static LocationBuilder atBuilding45Ship356Cabins() {
        return new LocationBuilder()
                .withBuilding(BuildingExamples.building45())
                .withShip(ShipExamples.ship3())
                .withShipArea(ShipAreaExamples.area56Cabins());
    }
}
