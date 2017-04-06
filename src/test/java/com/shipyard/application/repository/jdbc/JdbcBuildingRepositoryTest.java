package com.shipyard.application.repository.jdbc;

import com.shipyard.domain.data.Building;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcBuildingRepositoryTest extends JdbcRepositoryTest {
    private JdbcBuildingRepository repository;

    @Override
    protected void setUpInternal(JdbcDataSource dataSource) {
        repository = new JdbcBuildingRepository(dataSource);
    }

    @Test
    public void smokeTest() throws Exception {
        List<Building> buildings = repository.listAll();
        assertEquals(3, buildings.size());
        Building building1 = buildings.get(0);
        assertEquals("B 43", building1.getCode());
        assertEquals("Assembly shed", building1.getDescription());
        Building building2 = buildings.get(1);
        assertEquals("B 44", building2.getCode());
        assertEquals("Factory", building2.getDescription());
        Building building3 = buildings.get(2);
        assertEquals("B 45", building3.getCode());
        assertEquals("Paint shop", building3.getDescription());
    }
}
