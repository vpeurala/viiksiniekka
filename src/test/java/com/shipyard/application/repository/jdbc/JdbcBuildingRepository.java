package com.shipyard.application.repository.jdbc;

import com.shipyard.domain.data.Building;
import com.shipyard.domain.repository.BuildingRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.util.List;

public class JdbcBuildingRepository implements BuildingRepository {
    private final DataSource dataSource;

    public JdbcBuildingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Building> listAll() {
        DSLContext sql = DSL.using(dataSource, SQLDialect.H2);
        return sql.select().from("building").fetchInto(Building.class);
    }
}
