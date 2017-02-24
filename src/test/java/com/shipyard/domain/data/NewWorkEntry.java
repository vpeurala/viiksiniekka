package com.shipyard.domain.data;

import java.util.Objects;

/**
 * Entry about one worker, his/her location and energy requirements.
 */
public class NewWorkEntry {
    private final Long worker;
    private final NewLocation location;
    private final Occupation occupation;
    private final EnergyRequirements energyRequirements;

    public NewWorkEntry(
            Long worker,
            NewLocation location,
            Occupation occupation,
            EnergyRequirements energyRequirements) {
        Objects.requireNonNull(worker, "Field 'worker' of class NewWorkEntry cannot be null.");
        Objects.requireNonNull(location, "Field 'location' of class NewWorkEntry cannot be null.");
        Objects.requireNonNull(occupation, "Field 'occupation' of class NewWorkEntry cannot be null.");
        Objects.requireNonNull(energyRequirements, "Field 'energyRequirements' of class NewWorkEntry cannot be null.");
        this.worker = worker;
        this.location = location;
        this.occupation = occupation;
        this.energyRequirements = energyRequirements;
    }

    /**
     *
     */
    public Long getWorker() {
        return worker;
    }

    /**
     * The place where the worker will be working at, for example "Building 5 / Ship 2".
     */
    public NewLocation getLocation() {
        return location;
    }

    /**
     * The task which the worker will be performing.
     */
    public Occupation getOccupation() {
        return occupation;
    }

    /**
     * The energies that the worker requires to do his/her job.
     */
    public EnergyRequirements getEnergyRequirements() {
        return energyRequirements;
    }

    @Override
    public String toString() {
        return "NewWorkEntry{"
                + "worker='" + getWorker() + "'"
                + ", " + "location='" + getLocation() + "'"
                + ", " + "occupation='" + getOccupation() + "'"
                + ", " + "energyRequirements='" + getEnergyRequirements() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewWorkEntry other = (NewWorkEntry) o;

        if (!getWorker().equals(other.getWorker())) return false;
        if (!getLocation().equals(other.getLocation())) return false;
        if (!getOccupation().equals(other.getOccupation())) return false;
        if (!getEnergyRequirements().equals(other.getEnergyRequirements())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getWorker().hashCode();
        result = 31 * result + getLocation().hashCode();
        result = 31 * result + getOccupation().hashCode();
        result = 31 * result + getEnergyRequirements().hashCode();
        return result;
    }
}
