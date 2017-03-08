package com.shipyard.application.repository.jdbc;

import com.shipyard.domain.data.Company;
import com.shipyard.domain.repository.CompanyRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.util.List;

public class JdbcCompanyRepository implements CompanyRepository {
    private final DataSource dataSource;

    public JdbcCompanyRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Company> listAll() {
        DSLContext sql = DSL.using(dataSource, SQLDialect.H2);
        return sql.select().from("company").fetchInto(Company.class);
    }
}
