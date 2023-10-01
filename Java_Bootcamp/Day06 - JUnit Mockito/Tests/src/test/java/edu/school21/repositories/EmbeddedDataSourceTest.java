package edu.school21.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmbeddedDataSourceTest {
    DataSource ds;
    @BeforeEach
    void init() {
        ds = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
    }

    @Test
    void getConnectionToDataSource() throws SQLException {
        Connection conn = ds.getConnection();
        assertNotNull(conn);
        conn.close();
    }

    @AfterEach
    void closeDataSource() {
        ((EmbeddedDatabase)ds).shutdown();
    }
}
