package com.shipyard.domain.example;

import com.shipyard.domain.builder.BuildingBuilder;

public class BuildingExamples {
    public static BuildingBuilder building43() {
        return new BuildingBuilder()
                .withCode("B 43")
                .withDescription("Assembly shed");
    }

    public static BuildingBuilder building44() {
        return new BuildingBuilder()
                .withCode("B 44")
                .withDescription("Factory");
    }

    public static BuildingBuilder building45() {
        return new BuildingBuilder()
                .withCode("B 45")
                .withDescription("Paint shop");
    }
}
