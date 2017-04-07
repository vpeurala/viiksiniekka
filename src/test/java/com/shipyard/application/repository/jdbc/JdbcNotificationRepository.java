package com.shipyard.application.repository.jdbc;

import com.shipyard.domain.builder.*;
import com.shipyard.domain.data.Notification;
import com.shipyard.domain.data.NotificationStatus;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcNotificationRepository {
    private final DataSource dataSource;

    public JdbcNotificationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Notification> listAll() {
        DSLContext sql = getDsl();
        List<NotificationBuilder> notificationBuilders = sql.select().from("notification").fetch(new RecordMapper<Record, NotificationBuilder>() {
            @Override
            public NotificationBuilder map(Record record) {
                Long id = record.get("id", Long.class);
                NotificationStatus status = NotificationStatus.forValue(record.get("status", String.class));

                Integer workWeekWeekNumber = record.get("work_week_week_number", Integer.class);
                WorkweekBuilder workweekBuilder = new WorkweekBuilder();
                workweekBuilder.withWeekNumber(workWeekWeekNumber);

                WorkdayBuilder monday = new WorkdayBuilder();
                monday.getStartTime().withHour(record.get("work_week_monday_start_time_hour", Integer.class));
                monday.getStartTime().withMinute(record.get("work_week_monday_start_time_minute", Integer.class));
                monday.getEndTime().withHour(record.get("work_week_monday_end_time_hour", Integer.class));
                monday.getEndTime().withMinute(record.get("work_week_monday_end_time_minute", Integer.class));
                workweekBuilder.withMonday(monday);

                WorkdayBuilder wednesday = new WorkdayBuilder();
                wednesday.getStartTime().withHour(record.get("work_week_wednesday_start_time_hour", Integer.class));
                wednesday.getStartTime().withMinute(record.get("work_week_wednesday_start_time_minute", Integer.class));
                wednesday.getEndTime().withHour(record.get("work_week_wednesday_end_time_hour", Integer.class));
                wednesday.getEndTime().withMinute(record.get("work_week_wednesday_end_time_minute", Integer.class));
                workweekBuilder.withWednesday(wednesday);

                WorkdayBuilder sunday = new WorkdayBuilder();
                sunday.getStartTime().withHour(record.get("work_week_sunday_start_time_hour", Integer.class));
                sunday.getStartTime().withMinute(record.get("work_week_sunday_start_time_minute", Integer.class));
                sunday.getEndTime().withHour(record.get("work_week_sunday_end_time_hour", Integer.class));
                sunday.getEndTime().withMinute(record.get("work_week_sunday_end_time_minute", Integer.class));
                workweekBuilder.withSunday(sunday);

                NotificationBuilder builder = new NotificationBuilder();
                builder.withId(id);
                builder.withStatus(status);
                builder.withYardContact(contactPersonForId(record.get("yard_contact", Long.class)));
                builder.withSiteForeman(contactPersonForId(record.get("site_foreman", Long.class)));
                builder.withWorkWeek(workweekBuilder);
                builder.withAdditionalInformation(record.get("additional_information", String.class));

                builder.withWorkEntries(workEntriesForNotificationId(id));
                return builder;
            }
        });
        return notificationBuilders.stream().map(NotificationBuilder::build).collect(Collectors.toList());
    }

    private List<WorkEntryBuilder> workEntriesForNotificationId(Long id) {
        return getDsl().select().from("work_entry").where("notification = ?", id).fetch(new RecordMapper<Record, WorkEntryBuilder>() {
            @Override
            public WorkEntryBuilder map(Record record) {
                System.out.println(record);
                WorkEntryBuilder workEntryBuilder = new WorkEntryBuilder();
                workEntryBuilder.withId(record.get("id", Long.class));
                workEntryBuilder.withWorker(workerForId(record.get("worker", Long.class)));

                LocationBuilder locationBuilder = new LocationBuilder();
                locationBuilder.withBuilding(buildingForId(record.get("location_building", Long.class)));

                workEntryBuilder.withLocation(locationBuilder);
                return workEntryBuilder;
            }
        });
    }

    private BuildingBuilder buildingForId(Long id) {
        return getDsl().select().from("building").where("id = ?", id).fetchOne(new RecordMapper<Record, BuildingBuilder>() {
            @Override
            public BuildingBuilder map(Record record) {
                BuildingBuilder buildingBuilder = new BuildingBuilder();
                buildingBuilder.withId(record.get("id", Long.class));
                buildingBuilder.withCode(record.get("code", String.class));
                buildingBuilder.withDescription(record.get("description", String.class));
                return buildingBuilder;
            }
        });
    }

    private WorkerBuilder workerForId(Long id) {
        return getDsl().select().from("person").where("id = ?", id).fetchOne(new RecordMapper<Record, WorkerBuilder>() {
            @Override
            public WorkerBuilder map(Record record) {
                WorkerBuilder workerBuilder = new WorkerBuilder();
                workerBuilder.withId(record.get("id", Long.class));
                workerBuilder.withFirstName(record.get("first_name", String.class));
                workerBuilder.withLastName(record.get("last_name", String.class));
                workerBuilder.withCompany(companyForId(record.get("company", Long.class)));
                workerBuilder.withKeyCode(record.get("key_code", String.class));
                return workerBuilder;
            }
        });
    }

    private DSLContext getDsl() {
        Settings settings = new Settings().withRenderSchema(false).withRenderNameStyle(RenderNameStyle.AS_IS);
        return DSL.using(dataSource, SQLDialect.H2, settings);
    }

    private ContactPersonBuilder contactPersonForId(Long contactPersonId) {
        return getDsl().select().from("person").where("id = ?", contactPersonId).fetchOne(new RecordMapper<Record, ContactPersonBuilder>() {
            @Override
            public ContactPersonBuilder map(Record record) {
                Long id = record.get("id", Long.class);
                String firstName = record.get("first_name", String.class);
                String lastName = record.get("last_name", String.class);
                CompanyBuilder company = companyForId(record.get("company", Long.class));
                ContactInformationBuilder contactInformationBuilder = new ContactInformationBuilder();
                String email = record.get("contact_information_email", String.class);
                String phoneNumber = record.get("contact_information_phone_number", String.class);
                contactInformationBuilder.withEmail(email);
                contactInformationBuilder.withPhoneNumber(phoneNumber);
                return new ContactPersonBuilder().
                        withId(id).
                        withFirstName(firstName).
                        withLastName(lastName).
                        withContactInformation(contactInformationBuilder).
                        withCompany(company);
            }
        });
    }

    private CompanyBuilder companyForId(Long id) {
        return getDsl().select().from("company").where("id = ?", id).fetchOne(new RecordMapper<Record, CompanyBuilder>() {
            @Override
            public CompanyBuilder map(Record record) {
                return new CompanyBuilder().
                        withId(record.get("id", Long.class)).
                        withName(record.get("name", String.class));
            }
        });
    }
}
