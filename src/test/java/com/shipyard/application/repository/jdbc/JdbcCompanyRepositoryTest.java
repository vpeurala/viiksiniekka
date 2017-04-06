package com.shipyard.application.repository.jdbc;

import com.shipyard.domain.data.Company;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcCompanyRepositoryTest extends JdbcRepositoryTest {
    private JdbcCompanyRepository repository;

    @Override
    protected void setUpInternal(JdbcDataSource dataSource) {
        repository = new JdbcCompanyRepository(dataSource);
    }

    @Test
    public void smokeTest() throws Exception {
        List<Company> companies = repository.listAll();
        assertEquals(3, companies.size());
    }
}
