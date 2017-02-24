package com.shipyard.domain.builder;

import com.shipyard.domain.data.EnergyRequirements;
import com.shipyard.domain.data.Location;
import com.shipyard.domain.data.Occupation;
import com.shipyard.domain.data.WorkEntry;
import com.shipyard.domain.data.Worker;

import java.util.Objects;

public class WorkEntryBuilder {
    private Long id;
    private WorkerBuilder worker = new WorkerBuilder();
    private LocationBuilder location = new LocationBuilder();
    private Occupation occupation;
    private EnergyRequirementsBuilder energyRequirements = new EnergyRequirementsBuilder();

    public static WorkEntryBuilder from(WorkEntry workEntry) {
        return new WorkEntryBuilder()
                .withId(workEntry.getId())
                .withWorker(workEntry.getWorker())
                .withLocation(workEntry.getLocation())
                .withOccupation(workEntry.getOccupation())
                .withEnergyRequirements(workEntry.getEnergyRequirements());
    }

    /**
     * Surrogate key.
     */
    public WorkEntryBuilder withId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
        return this;
    }

    /**
     *
     */
    public WorkerBuilder getWorker() {
        return worker;
    }

    /**
     *
     */
    public WorkEntryBuilder withWorker(WorkerBuilder worker) {
        Objects.requireNonNull(worker);
        this.worker = worker;
        return this;
    }

    /**
     *
     */
    public WorkEntryBuilder withWorker(Worker worker) {
        Objects.requireNonNull(worker);
        this.worker = WorkerBuilder.from(worker);
        return this;
    }

    /**
     * The place where the worker will be working at, for example "Building 5 / Ship 2".
     */
    public LocationBuilder getLocation() {
        return location;
    }

    /**
     * The place where the worker will be working at, for example "Building 5 / Ship 2".
     */
    public WorkEntryBuilder withLocation(LocationBuilder location) {
        Objects.requireNonNull(location);
        this.location = location;
        return this;
    }

    /**
     * The place where the worker will be working at, for example "Building 5 / Ship 2".
     */
    public WorkEntryBuilder withLocation(Location location) {
        Objects.requireNonNull(location);
        this.location = LocationBuilder.from(location);
        return this;
    }

    /**
     * The task which the worker will be performing.
     */
    public WorkEntryBuilder withOccupation(Occupation occupation) {
        Objects.requireNonNull(occupation);
        this.occupation = occupation;
        return this;
    }

    /**
     * The energies that the worker requires to do his/her job.
     */
    public EnergyRequirementsBuilder getEnergyRequirements() {
        return energyRequirements;
    }

    /**
     * The energies that the worker requires to do his/her job.
     */
    public WorkEntryBuilder withEnergyRequirements(EnergyRequirementsBuilder energyRequirements) {
        Objects.requireNonNull(energyRequirements);
        this.energyRequirements = energyRequirements;
        return this;
    }

    /**
     * The energies that the worker requires to do his/her job.
     */
    public WorkEntryBuilder withEnergyRequirements(EnergyRequirements energyRequirements) {
        Objects.requireNonNull(energyRequirements);
        this.energyRequirements = EnergyRequirementsBuilder.from(energyRequirements);
        return this;
    }

    public WorkEntry build() {
        return new WorkEntry(id, worker.build(), location.build(), occupation, energyRequirements.build());
    }
}
