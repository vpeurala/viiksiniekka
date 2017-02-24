package com.shipyard.domain.example;

import com.shipyard.domain.builder.ShipAreaBuilder;

public class ShipAreaExamples {
    public static ShipAreaBuilder area56Cabins() {
        return new ShipAreaBuilder()
                .withCode("56")
                .withDescription("Cabins");
    }
}
