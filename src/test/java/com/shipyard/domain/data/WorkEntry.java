package com.shipyard.domain.data;

import java.util.Objects;

/**
 * Entry about one worker, his/her location and energy requirements.
 */
public class WorkEntry {
    private final Long id;
    private final Worker worker;
    private final Location location;
    private final Occupation occupation;
    private final EnergyRequirements energyRequirements;

    public WorkEntry(
            Long id,
            Worker worker,
            Location location,
            Occupation occupation,
            EnergyRequirements energyRequirements) {
        Objects.requireNonNull(id, "Field 'id' of class WorkEntry cannot be null.");
        Objects.requireNonNull(worker, "Field 'worker' of class WorkEntry cannot be null.");
        Objects.requireNonNull(location, "Field 'location' of class WorkEntry cannot be null.");
        Objects.requireNonNull(occupation, "Field 'occupation' of class WorkEntry cannot be null.");
        Objects.requireNonNull(energyRequirements, "Field 'energyRequirements' of class WorkEntry cannot be null.");
        this.id = id;
        this.worker = worker;
        this.location = location;
        this.occupation = occupation;
        this.energyRequirements = energyRequirements;
    }

    /**
     * Surrogate key.
     */
    public Long getId() {
        return id;
    }

    /**
     *
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     * The place where the worker will be working at, for example "Building 5 / Ship 2".
     */
    public Location getLocation() {
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
        return "WorkEntry{"
                + "id='" + getId() + "'"
                + ", " + "worker='" + getWorker() + "'"
                + ", " + "location='" + getLocation() + "'"
                + ", " + "occupation='" + getOccupation() + "'"
                + ", " + "energyRequirements='" + getEnergyRequirements() + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkEntry other = (WorkEntry) o;

        if (!getId().equals(other.getId())) return false;
        if (!getWorker().equals(other.getWorker())) return false;
        if (!getLocation().equals(other.getLocation())) return false;
        if (!getOccupation().equals(other.getOccupation())) return false;
        if (!getEnergyRequirements().equals(other.getEnergyRequirements())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + getId().hashCode();
        result = 31 * result + getWorker().hashCode();
        result = 31 * result + getLocation().hashCode();
        result = 31 * result + getOccupation().hashCode();
        result = 31 * result + getEnergyRequirements().hashCode();
        return result;
    }
}
