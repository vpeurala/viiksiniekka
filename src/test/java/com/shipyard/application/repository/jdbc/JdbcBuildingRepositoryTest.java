package com.shipyard.application.repository.jdbc;

import com.shipyard.domain.data.Building;
import org.apache.commons.io.IOUtils;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcBuildingRepositoryTest {
    private JdbcBuildingRepository repository;

    @Before
    public void setUp() throws Exception {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:shipyard");
        String ddl1 = stringResource("/ddl/V1__Create_Tables_ShipYard.sql");
        executeDdl(dataSource, ddl1);
        String ddl2 = stringResource("/ddl/building_insert.sql");
        executeDdl(dataSource, ddl2);
        repository = new JdbcBuildingRepository(dataSource);
    }

    private void executeDdl(JdbcDataSource dataSource, String ddl) throws SQLException {
        String[] statements = ddl.split(";");
        Connection connection = dataSource.getConnection();
        for (String originalStatement : statements) {
            // Note: TIMESTAMP WITHOUT TIME ZONE is a PostgreSQL datatype, the equivalent H2 datatype is just simple TIMESTAMP.
            String modifiedStatement = originalStatement.trim().replaceAll("TIMESTAMP WITHOUT TIME ZONE", "TIMESTAMP");
            connection.createStatement().execute(modifiedStatement);
        }
    }

    @Test
    public void smokeTest() throws Exception {
        List<Building> buildings = repository.listAll();
        assertEquals(3, buildings.size());
        Building building1 = buildings.get(0);
        assertEquals("B 43", building1.getCode());
        assertEquals("Assembly shed", building1.getDescription());
    }

    private String stringResource(String name) throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream(name);
        if (resourceAsStream == null) {
            throw new FileNotFoundException(name);
        }
        return IOUtils.toString(resourceAsStream, "utf-8");
    }
}
