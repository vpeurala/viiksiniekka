package com.shipyard.domain.visitor;

import com.shipyard.domain.data.NotificationStatus;

public interface NotificationStatusVisitor<T> {
    T visitApproved(NotificationStatus notificationStatus);

    T visitDraft(NotificationStatus notificationStatus);

    T visitRejected(NotificationStatus notificationStatus);

    T visitWaitingForYardContact(NotificationStatus notificationStatus);

    T visitWaitingForProductionManager(NotificationStatus notificationStatus);
}
