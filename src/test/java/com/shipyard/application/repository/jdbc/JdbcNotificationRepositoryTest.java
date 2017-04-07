package com.shipyard.application.repository.jdbc;

import com.shipyard.domain.data.*;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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
        assertWorkWeek(notification1.getWorkWeek());
        assertEquals("Sunday work is needed because we are behind schedule.", notification1.getAdditionalInformation());
        assertWorkEntries(notification1.getWorkEntries());
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

    private void assertWorkWeek(Workweek workWeek) {
        assertEquals(48, (int) workWeek.getWeekNumber());

        assertEquals(10, (int) workWeek.getMonday().get().getStartTime().getHour());
        assertEquals(15, (int) workWeek.getMonday().get().getStartTime().getMinute());
        assertEquals(18, (int) workWeek.getMonday().get().getEndTime().getHour());
        assertEquals(30, (int) workWeek.getMonday().get().getEndTime().getMinute());

        assertFalse(workWeek.getTuesday().isPresent());

        assertEquals(7, (int) workWeek.getWednesday().get().getStartTime().getHour());
        assertEquals(0, (int) workWeek.getWednesday().get().getStartTime().getMinute());
        assertEquals(16, (int) workWeek.getWednesday().get().getEndTime().getHour());
        assertEquals(20, (int) workWeek.getWednesday().get().getEndTime().getMinute());

        assertFalse(workWeek.getThursday().isPresent());

        assertFalse(workWeek.getFriday().isPresent());

        assertFalse(workWeek.getSaturday().isPresent());

        assertEquals(10, (int) workWeek.getSunday().get().getStartTime().getHour());
        assertEquals(0, (int) workWeek.getSunday().get().getStartTime().getMinute());
        assertEquals(14, (int) workWeek.getSunday().get().getEndTime().getHour());
        assertEquals(0, (int) workWeek.getSunday().get().getEndTime().getMinute());
    }

    private void assertWorkEntries(List<WorkEntry> workEntries) {
        assertEquals(2, workEntries.size());

        WorkEntry workEntry1 = workEntries.get(0);
        assertEquals(1, (long) workEntry1.getId());
        EnergyRequirements energyRequirements1 = workEntry1.getEnergyRequirements();
        assertTrue(energyRequirements1.getOxyacetylene());
        assertFalse(energyRequirements1.getCompositeGas());
        assertTrue(energyRequirements1.getArgon());
        assertFalse(energyRequirements1.getCompressedAir());
        assertTrue(energyRequirements1.getHotWorks());
        Worker worker1 = workEntry1.getWorker();
        assertEquals(7, (long) worker1.getId());
        assertEquals("Jurij", worker1.getFirstName());
        assertEquals("Andrejev", worker1.getLastName());
        assertEquals(1, (long) worker1.getCompany().getId());
        assertEquals("Ablemans", worker1.getCompany().getName());
        assertEquals("4060", worker1.getKeyCode());
        assertEquals(2, (long) workEntry1.getLocation().getBuilding().getId());
        assertEquals("B 44", workEntry1.getLocation().getBuilding().getCode());
        assertEquals("Factory", workEntry1.getLocation().getBuilding().getDescription());

        WorkEntry workEntry2 = workEntries.get(1);
        assertEquals(2, (long) workEntry2.getId());
        EnergyRequirements energyRequirements2 = workEntry2.getEnergyRequirements();
        assertFalse(energyRequirements2.getOxyacetylene());
        assertFalse(energyRequirements2.getCompositeGas());
        assertFalse(energyRequirements2.getArgon());
        assertTrue(energyRequirements2.getCompressedAir());
        assertFalse(energyRequirements2.getHotWorks());
        Worker worker2 = workEntry2.getWorker();
        assertEquals(8, (long) worker2.getId());
        assertEquals("Genadij", worker2.getFirstName());
        assertEquals("Bogira", worker2.getLastName());
        assertEquals(2, (long) worker2.getCompany().getId());
        assertEquals("Maersk", worker2.getCompany().getName());
        assertEquals("2332", worker2.getKeyCode());
        assertEquals(1, (long) workEntry2.getLocation().getBuilding().getId());
        assertEquals("B 43", workEntry2.getLocation().getBuilding().getCode());
        assertEquals("Assembly shed", workEntry2.getLocation().getBuilding().getDescription());
    }
}
