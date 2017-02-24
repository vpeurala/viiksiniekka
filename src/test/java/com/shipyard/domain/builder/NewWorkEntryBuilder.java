package com.shipyard.domain.builder;

import com.shipyard.domain.data.EnergyRequirements;
import com.shipyard.domain.data.NewLocation;
import com.shipyard.domain.data.NewWorkEntry;
import com.shipyard.domain.data.Occupation;

import java.util.Objects;

public class NewWorkEntryBuilder {
    private Long worker;
    private NewLocationBuilder location = new NewLocationBuilder();
    private Occupation occupation;
    private EnergyRequirementsBuilder energyRequirements = new EnergyRequirementsBuilder();

    public static NewWorkEntryBuilder from(NewWorkEntry newWorkEntry) {
        return new NewWorkEntryBuilder()
                .withWorker(newWorkEntry.getWorker())
                .withLocation(newWorkEntry.getLocation())
                .withOccupation(newWorkEntry.getOccupation())
                .withEnergyRequirements(newWorkEntry.getEnergyRequirements());
    }

    /**
     *
     */
    public NewWorkEntryBuilder withWorker(Long worker) {
        Objects.requireNonNull(worker);
        this.worker = worker;
        return this;
    }

    /**
     * The place where the worker will be working at, for example "Building 5 / Ship 2".
     */
    public NewLocationBuilder getLocation() {
        return location;
    }

    /**
     * The place where the worker will be working at, for example "Building 5 / Ship 2".
     */
    public NewWorkEntryBuilder withLocation(NewLocationBuilder location) {
        Objects.requireNonNull(location);
        this.location = location;
        return this;
    }

    /**
     * The place where the worker will be working at, for example "Building 5 / Ship 2".
     */
    public NewWorkEntryBuilder withLocation(NewLocation location) {
        Objects.requireNonNull(location);
        this.location = NewLocationBuilder.from(location);
        return this;
    }

    /**
     * The task which the worker will be performing.
     */
    public NewWorkEntryBuilder withOccupation(Occupation occupation) {
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
    public NewWorkEntryBuilder withEnergyRequirements(EnergyRequirementsBuilder energyRequirements) {
        Objects.requireNonNull(energyRequirements);
        this.energyRequirements = energyRequirements;
        return this;
    }

    /**
     * The energies that the worker requires to do his/her job.
     */
    public NewWorkEntryBuilder withEnergyRequirements(EnergyRequirements energyRequirements) {
        Objects.requireNonNull(energyRequirements);
        this.energyRequirements = EnergyRequirementsBuilder.from(energyRequirements);
        return this;
    }

    public NewWorkEntry build() {
        return new NewWorkEntry(worker, location.build(), occupation, energyRequirements.build());
    }
}
