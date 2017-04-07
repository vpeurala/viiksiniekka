package com.shipyard.application.repository.jdbc;

import org.apache.commons.io.IOUtils;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class JdbcRepositoryTest {
    @Before
    public final void setUp() throws Exception {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:shipyard;database_to_upper=false;mode=PostgreSQL;schema=PUBLIC");
        String ddl1 = stringResource("/ddl/V1__Create_Tables_ShipYard.sql");
        executeDdl(dataSource, ddl1);
        String ddl2 = stringResource("/ddl/V2__Insert_Test_Data_ShipYard.sql");
        executeDdl(dataSource, ddl2);
        setUpInternal(dataSource);
    }

    protected abstract void setUpInternal(JdbcDataSource dataSource);

    private void executeDdl(JdbcDataSource dataSource, String ddl) throws SQLException {
        String[] statements = ddl.split(";");
        Connection connection = dataSource.getConnection();
        for (String originalStatement : statements) {
            // Note: TIMESTAMP WITHOUT TIME ZONE is a PostgreSQL datatype, the equivalent H2 datatype is just simple TIMESTAMP.
            String modifiedStatement = originalStatement.trim().replaceAll("TIMESTAMP WITHOUT TIME ZONE", "TIMESTAMP");
            connection.createStatement().execute(modifiedStatement);
        }
    }

    private String stringResource(String name) throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream(name);
        if (resourceAsStream == null) {
            throw new FileNotFoundException(name);
        }
        return IOUtils.toString(resourceAsStream, "utf-8");
    }
}
