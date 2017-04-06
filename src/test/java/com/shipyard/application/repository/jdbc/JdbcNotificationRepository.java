package com.shipyard.application.repository.jdbc;

import com.shipyard.domain.data.Notification;
import com.shipyard.domain.data.NotificationStatus;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.util.List;

public class JdbcNotificationRepository {
    private final DataSource dataSource;

    public JdbcNotificationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Notification> listAll() {
        DSLContext sql = DSL.using(dataSource, SQLDialect.H2);
        return sql.select().from("notification INNER JOIN log_entry ON log_entry.notification = notification.id INNER JOIN work_entry ON work_entry.notification = notification.id").fetch(new RecordMapper<Record, Notification>() {
            @Override
            public Notification map(Record record) {
                Long id = record.get("notification.id", Long.class);
                NotificationStatus notificationStatus = NotificationStatus.forValue(record.get("status", String.class));
                return null;
            }
        });
    }
}
