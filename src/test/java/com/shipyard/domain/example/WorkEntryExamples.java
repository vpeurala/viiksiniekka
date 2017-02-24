package com.shipyard.domain.example;

import com.shipyard.domain.builder.WorkEntryBuilder;
import com.shipyard.domain.data.Occupation;

public class WorkEntryExamples {
    public static WorkEntryBuilder jurijAsAWelderInBuilding44Ship2() {
        return new WorkEntryBuilder()
                .withWorker(WorkerExamples.jurijAndrejev())
                .withLocation(LocationExamples.atBuilding44Ship2())
                .withOccupation(Occupation.WELDER)
                .withEnergyRequirements(EnergyRequirementsExamples.energyRequirementsForAWelder());
    }

    public static WorkEntryBuilder genadijAsAFitterInBuilding43() {
        return new WorkEntryBuilder()
                .withWorker(WorkerExamples.genadijBogira())
                .withLocation(LocationExamples.atBuilding43())
                .withOccupation(Occupation.FITTER)
                .withEnergyRequirements(EnergyRequirementsExamples.energyRequirementsForAFitter());
    }
}
