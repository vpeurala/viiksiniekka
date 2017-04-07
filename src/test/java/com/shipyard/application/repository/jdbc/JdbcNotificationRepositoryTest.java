package com.shipyard.application.repository.jdbc;

import com.shipyard.domain.data.Notification;
import com.shipyard.domain.data.NotificationStatus;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcNotificationRepositoryTest extends JdbcRepositoryTest {
    private JdbcNotificationRepository repository;

    @Override
    protected void setUpInternal(JdbcDataSource dataSource) {
        repository = new JdbcNotificationRepository(dataSource);
    }

    @Test
    public void smokeTest() throws Exception {
        List<Notification> notifications = repository.listAll();
        assertEquals(1, notifications.size());
        Notification notification1 = notifications.get(0);
        assertEquals(1, (long) notification1.getId());
        assertEquals(NotificationStatus.APPROVED, notification1.getStatus());
    }
}
