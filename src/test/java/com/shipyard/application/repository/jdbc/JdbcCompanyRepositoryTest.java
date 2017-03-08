package com.shipyard.application.repository.jdbc;

import com.shipyard.domain.data.Company;
import org.apache.commons.io.IOUtils;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcCompanyRepositoryTest {
    private JdbcCompanyRepository repository;

    @Before
    public void setUp() throws Exception {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:shipyard");
        String ddl = stringResource("/ddl/V1__Create_Tables_ShipYard.sql");
        String[] statements = ddl.split(";");
        Connection connection = dataSource.getConnection();
        for (String originalStatement : statements) {
            // Note: TIMESTAMP WITHOUT TIME ZONE is a PostgreSQL datatype, the equivalent H2 datatype is just simple TIMESTAMP.
            String modifiedStatement = originalStatement.trim().replaceAll("TIMESTAMP WITHOUT TIME ZONE", "TIMESTAMP");
            connection.createStatement().execute(modifiedStatement);
            System.out.println("Executed SQL statement: '" + modifiedStatement + "'.");
        }
        repository = new JdbcCompanyRepository(dataSource);
    }

    @Test
    public void smokeTest() throws Exception {
        List<Company> companies = repository.listAll();
        assertEquals(3, companies.size());
    }

    private String stringResource(String name) throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream(name);
        if (resourceAsStream == null) {
            throw new FileNotFoundException(name);
        }
        return IOUtils.toString(resourceAsStream, "utf-8");
    }
}
