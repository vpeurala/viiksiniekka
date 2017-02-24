package com.shipyard.domain.data;

import java.util.Objects;

/**
 * The "energies" (gases, compressed air, hot works) a worker needs.
 */
public class EnergyRequirements {
    private final Boolean oxyacetylene;
    private final Boolean compositeGas;
    private final Boolean argon;
    private final Boolean compressedAir;
    private final Boolean hotWorks;

    public EnergyRequirements(
            Boolean oxyacetylene,
            Boolean compositeGas,
            Boolean argon,
            Boolean compressedAir,
            Boolean hotWorks) {
        Objects.requireNonNull(oxyacetylene, "Field 'oxyacetylene' of class EnergyRequirements cannot be null.");
        Objects.requireNonNull(compositeGas, "Field 'compositeGas' of class EnergyRequirements cannot be null.");
        Objects.requireNonNull(argon, "Field 'argon' of class EnergyRequirements cannot be null.");
        Objects.requireNonNull(compressedAir, "Field 'compressedAir' of class EnergyRequirements cannot be null.");
        Objects.requireNonNull(hotWorks, "Field 'hotWorks' of class EnergyRequirements cannot be null.");
        this.oxyacetylene = oxyacetylene;
        this.compositeGas = compositeGas;
        this.argon = argon;
        this.compressedAir = compressedAir;
        this.hotWorks = hotWorks;
    }

    /**
     *
     */
    public Boolean getOxyacetylene() {
        return oxyacetylene;
    }

    /**
     *
     */
    public Boolean getCompositeGas() {
        return compositeGas;
    }

    /**
     *
     */
    public Boolean getArgon() {
        return argon;
    }

    /**
     *
     */
    public Boolean getCompressedAir() {
        return compressedAir;
    }

    /**
     *
     */
    public Boolean getHotWorks() {
        return hotWorks;
    }

    @Override
    public String toString() {
        return "EnergyRequirements{"
                + "oxyacetylene='" + getOxyacetylene() + "'"
                + ", " + "compositeGas='" + getCompositeGas() + "'"
                + ", " + "argon='" + getArgon() + "'"
                + ", " + "compressedAir='" + getCompressedAir() + "'"
                + ", " + "hotWorks='" + getHotWorks() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnergyRequirements other = (EnergyRequirements) o;

        if (!getOxyacetylene().equals(other.getOxyacetylene())) return false;
        if (!getCompositeGas().equals(other.getCompositeGas())) return false;
        if (!getArgon().equals(other.getArgon())) return false;
        if (!getCompressedAir().equals(other.getCompressedAir())) return false;
        if (!getHotWorks().equals(other.getHotWorks())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getOxyacetylene().hashCode();
        result = 31 * result + getCompositeGas().hashCode();
        result = 31 * result + getArgon().hashCode();
        result = 31 * result + getCompressedAir().hashCode();
        result = 31 * result + getHotWorks().hashCode();
        return result;
    }
}
