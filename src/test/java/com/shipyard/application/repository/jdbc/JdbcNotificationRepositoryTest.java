package com.shipyard.application.repository.jdbc;

import com.shipyard.domain.data.ContactPerson;
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
        assertYardContact(notification1.getYardContact());
        assertSiteForeman(notification1.getSiteForeman());
    }

    private void assertYardContact(ContactPerson yardContact) {
        assertEquals(4, (long) yardContact.getId());
        assertEquals("Tero", yardContact.getFirstName());
        assertEquals("Packalen", yardContact.getLastName());
        assertEquals(2, (long) yardContact.getCompany().getId());
        assertEquals("Maersk", yardContact.getCompany().getName());
        assertEquals("tero.packalen@yard.com", yardContact.getContactInformation().getEmail());
        assertEquals("040 - 568 3313", yardContact.getContactInformation().getPhoneNumber());        
    }

    private void assertSiteForeman(ContactPerson siteForeman) {
        assertEquals(3, (long) siteForeman.getId());
        assertEquals("Ville", siteForeman.getFirstName());
        assertEquals("Peurala", siteForeman.getLastName());
        assertEquals(3, (long) siteForeman.getCompany().getId());
        assertEquals("STX Group", siteForeman.getCompany().getName());
        assertEquals("ville.peurala@mail.com", siteForeman.getContactInformation().getEmail());
        assertEquals("050 - 352 7878", siteForeman.getContactInformation().getPhoneNumber());
    }

    /*
                <example name="Notification 1">
                    <fieldvalue field="status">Approved</fieldvalue>
                    <fieldvalue field="yardContact" ref="Tero Packalen with contact information"/>
                    <fieldvalue field="siteForeman" ref="Ville Peurala with contact information"/>
                    <fieldvalue field="additionalInformation">Sunday work is needed because we are behind schedule.</fieldvalue>
                    <fieldvalue field="workWeek" ref="Week 48"/>
                    <fieldvalue field="workEntries">
                        <list>
                            <entry ref="Jurij as a welder in building 44 / ship 2"/>
                            <entry ref="Genadij as a fitter in building 43"/>
                        </list>
                    </fieldvalue>
                    <fieldvalue field="log">
                        <list>
                            <entry ref="Creation of notification 1"/>
                            <entry ref="Sending of notification 1"/>
                        </list>
                    </fieldvalue>
                </example>
     */
}
