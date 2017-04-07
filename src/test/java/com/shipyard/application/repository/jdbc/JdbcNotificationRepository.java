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
                return builder;
            }
        });
        return notificationBuilders.stream().map(NotificationBuilder::build).collect(Collectors.toList());
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
