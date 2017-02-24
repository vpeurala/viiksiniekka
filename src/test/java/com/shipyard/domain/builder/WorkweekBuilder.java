package com.shipyard.domain.builder;

import com.shipyard.domain.data.Workday;
import com.shipyard.domain.data.Workweek;

import java.util.Objects;
import java.util.Optional;

public class WorkweekBuilder {
    private Integer weekNumber;
    private Optional<WorkdayBuilder> monday = Optional.empty();
    private Optional<WorkdayBuilder> tuesday = Optional.empty();
    private Optional<WorkdayBuilder> wednesday = Optional.empty();
    private Optional<WorkdayBuilder> thursday = Optional.empty();
    private Optional<WorkdayBuilder> friday = Optional.empty();
    private Optional<WorkdayBuilder> saturday = Optional.empty();
    private Optional<WorkdayBuilder> sunday = Optional.empty();

    public static WorkweekBuilder from(Workweek workweek) {
        return new WorkweekBuilder()
                .withWeekNumber(workweek.getWeekNumber())
                .withMondayOpt(workweek.getMonday())
                .withTuesdayOpt(workweek.getTuesday())
                .withWednesdayOpt(workweek.getWednesday())
                .withThursdayOpt(workweek.getThursday())
                .withFridayOpt(workweek.getFriday())
                .withSaturdayOpt(workweek.getSaturday())
                .withSundayOpt(workweek.getSunday());
    }

    /**
     * A ISO week number, minimum is 1, maximum is 53.
     */
    public WorkweekBuilder withWeekNumber(Integer weekNumber) {
        Objects.requireNonNull(weekNumber);
        this.weekNumber = weekNumber;
        return this;
    }

    /**
     *
     */
    public Optional<WorkdayBuilder> getMonday() {
        return monday;
    }

    /**
     *
     */
    public WorkweekBuilder withMonday(WorkdayBuilder monday) {
        Objects.requireNonNull(monday);
        this.monday = Optional.of(monday);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withMonday(Workday monday) {
        Objects.requireNonNull(monday);
        this.monday = Optional.of(WorkdayBuilder.from(monday));
        return this;
    }

    /**
     *
     */
    private WorkweekBuilder withMondayOpt(Optional<Workday> monday) {
        Objects.requireNonNull(monday);
        this.monday = monday.map(WorkdayBuilder::from);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withoutMonday() {
        this.monday = Optional.empty();
        return this;
    }

    /**
     *
     */
    public Optional<WorkdayBuilder> getTuesday() {
        return tuesday;
    }

    /**
     *
     */
    public WorkweekBuilder withTuesday(WorkdayBuilder tuesday) {
        Objects.requireNonNull(tuesday);
        this.tuesday = Optional.of(tuesday);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withTuesday(Workday tuesday) {
        Objects.requireNonNull(tuesday);
        this.tuesday = Optional.of(WorkdayBuilder.from(tuesday));
        return this;
    }

    /**
     *
     */
    private WorkweekBuilder withTuesdayOpt(Optional<Workday> tuesday) {
        Objects.requireNonNull(tuesday);
        this.tuesday = tuesday.map(WorkdayBuilder::from);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withoutTuesday() {
        this.tuesday = Optional.empty();
        return this;
    }

    /**
     *
     */
    public Optional<WorkdayBuilder> getWednesday() {
        return wednesday;
    }

    /**
     *
     */
    public WorkweekBuilder withWednesday(WorkdayBuilder wednesday) {
        Objects.requireNonNull(wednesday);
        this.wednesday = Optional.of(wednesday);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withWednesday(Workday wednesday) {
        Objects.requireNonNull(wednesday);
        this.wednesday = Optional.of(WorkdayBuilder.from(wednesday));
        return this;
    }

    /**
     *
     */
    private WorkweekBuilder withWednesdayOpt(Optional<Workday> wednesday) {
        Objects.requireNonNull(wednesday);
        this.wednesday = wednesday.map(WorkdayBuilder::from);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withoutWednesday() {
        this.wednesday = Optional.empty();
        return this;
    }

    /**
     *
     */
    public Optional<WorkdayBuilder> getThursday() {
        return thursday;
    }

    /**
     *
     */
    public WorkweekBuilder withThursday(WorkdayBuilder thursday) {
        Objects.requireNonNull(thursday);
        this.thursday = Optional.of(thursday);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withThursday(Workday thursday) {
        Objects.requireNonNull(thursday);
        this.thursday = Optional.of(WorkdayBuilder.from(thursday));
        return this;
    }

    /**
     *
     */
    private WorkweekBuilder withThursdayOpt(Optional<Workday> thursday) {
        Objects.requireNonNull(thursday);
        this.thursday = thursday.map(WorkdayBuilder::from);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withoutThursday() {
        this.thursday = Optional.empty();
        return this;
    }

    /**
     *
     */
    public Optional<WorkdayBuilder> getFriday() {
        return friday;
    }

    /**
     *
     */
    public WorkweekBuilder withFriday(WorkdayBuilder friday) {
        Objects.requireNonNull(friday);
        this.friday = Optional.of(friday);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withFriday(Workday friday) {
        Objects.requireNonNull(friday);
        this.friday = Optional.of(WorkdayBuilder.from(friday));
        return this;
    }

    /**
     *
     */
    private WorkweekBuilder withFridayOpt(Optional<Workday> friday) {
        Objects.requireNonNull(friday);
        this.friday = friday.map(WorkdayBuilder::from);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withoutFriday() {
        this.friday = Optional.empty();
        return this;
    }

    /**
     *
     */
    public Optional<WorkdayBuilder> getSaturday() {
        return saturday;
    }

    /**
     *
     */
    public WorkweekBuilder withSaturday(WorkdayBuilder saturday) {
        Objects.requireNonNull(saturday);
        this.saturday = Optional.of(saturday);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withSaturday(Workday saturday) {
        Objects.requireNonNull(saturday);
        this.saturday = Optional.of(WorkdayBuilder.from(saturday));
        return this;
    }

    /**
     *
     */
    private WorkweekBuilder withSaturdayOpt(Optional<Workday> saturday) {
        Objects.requireNonNull(saturday);
        this.saturday = saturday.map(WorkdayBuilder::from);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withoutSaturday() {
        this.saturday = Optional.empty();
        return this;
    }

    /**
     *
     */
    public Optional<WorkdayBuilder> getSunday() {
        return sunday;
    }

    /**
     *
     */
    public WorkweekBuilder withSunday(WorkdayBuilder sunday) {
        Objects.requireNonNull(sunday);
        this.sunday = Optional.of(sunday);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withSunday(Workday sunday) {
        Objects.requireNonNull(sunday);
        this.sunday = Optional.of(WorkdayBuilder.from(sunday));
        return this;
    }

    /**
     *
     */
    private WorkweekBuilder withSundayOpt(Optional<Workday> sunday) {
        Objects.requireNonNull(sunday);
        this.sunday = sunday.map(WorkdayBuilder::from);
        return this;
    }

    /**
     *
     */
    public WorkweekBuilder withoutSunday() {
        this.sunday = Optional.empty();
        return this;
    }

    public Workweek build() {
        return new Workweek(weekNumber, monday.map(WorkdayBuilder::build), tuesday.map(WorkdayBuilder::build), wednesday.map(WorkdayBuilder::build), thursday.map(WorkdayBuilder::build), friday.map(WorkdayBuilder::build), saturday.map(WorkdayBuilder::build), sunday.map(WorkdayBuilder::build));
    }
}
