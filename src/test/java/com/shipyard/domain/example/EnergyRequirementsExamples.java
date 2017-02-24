package com.shipyard.domain.example;

import com.shipyard.domain.builder.EnergyRequirementsBuilder;

public class EnergyRequirementsExamples {
    public static EnergyRequirementsBuilder energyRequirementsForAWelder() {
        return new EnergyRequirementsBuilder()
                .withOxyacetylene(true)
                .withCompositeGas(false)
                .withArgon(true)
                .withCompressedAir(false)
                .withHotWorks(true);
    }

    public static EnergyRequirementsBuilder energyRequirementsForAFitter() {
        return new EnergyRequirementsBuilder()
                .withOxyacetylene(false)
                .withCompositeGas(false)
                .withArgon(false)
                .withCompressedAir(true)
                .withHotWorks(false);
    }
}
