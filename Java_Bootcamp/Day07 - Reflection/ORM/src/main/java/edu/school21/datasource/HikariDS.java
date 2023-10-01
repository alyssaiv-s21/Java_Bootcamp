package edu.school21.datasource;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariDS {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/alyssaiv";
    private static final String DB_USER = "alyssaiv";
    private static final String DB_PASSWORD = "";
    public static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        return new HikariDataSource(config);
    }
}
