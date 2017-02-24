package com.shipyard.domain.builder;

import com.shipyard.domain.data.EnergyRequirements;

import java.util.Objects;

public class EnergyRequirementsBuilder {
    private Boolean oxyacetylene;
    private Boolean compositeGas;
    private Boolean argon;
    private Boolean compressedAir;
    private Boolean hotWorks;

    public static EnergyRequirementsBuilder from(EnergyRequirements energyRequirements) {
        return new EnergyRequirementsBuilder()
                .withOxyacetylene(energyRequirements.getOxyacetylene())
                .withCompositeGas(energyRequirements.getCompositeGas())
                .withArgon(energyRequirements.getArgon())
                .withCompressedAir(energyRequirements.getCompressedAir())
                .withHotWorks(energyRequirements.getHotWorks());
    }

    /**
     *
     */
    public EnergyRequirementsBuilder withOxyacetylene(Boolean oxyacetylene) {
        Objects.requireNonNull(oxyacetylene);
        this.oxyacetylene = oxyacetylene;
        return this;
    }

    /**
     *
     */
    public EnergyRequirementsBuilder withCompositeGas(Boolean compositeGas) {
        Objects.requireNonNull(compositeGas);
        this.compositeGas = compositeGas;
        return this;
    }

    /**
     *
     */
    public EnergyRequirementsBuilder withArgon(Boolean argon) {
        Objects.requireNonNull(argon);
        this.argon = argon;
        return this;
    }

    /**
     *
     */
    public EnergyRequirementsBuilder withCompressedAir(Boolean compressedAir) {
        Objects.requireNonNull(compressedAir);
        this.compressedAir = compressedAir;
        return this;
    }

    /**
     *
     */
    public EnergyRequirementsBuilder withHotWorks(Boolean hotWorks) {
        Objects.requireNonNull(hotWorks);
        this.hotWorks = hotWorks;
        return this;
    }

    public EnergyRequirements build() {
        return new EnergyRequirements(oxyacetylene, compositeGas, argon, compressedAir, hotWorks);
    }
}
