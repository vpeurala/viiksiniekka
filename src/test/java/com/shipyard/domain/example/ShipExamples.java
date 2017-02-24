package com.shipyard.domain.example;

import com.shipyard.domain.builder.ShipBuilder;

public class ShipExamples {
    public static ShipBuilder ship2() {
        return new ShipBuilder()
                .withCode("N 2")
                .withDescription("Ship 2");
    }

    public static ShipBuilder ship3() {
        return new ShipBuilder()
                .withCode("R 3")
                .withDescription("Ship 3");
    }
}
