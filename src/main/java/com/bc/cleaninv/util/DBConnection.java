package com.bc.cleaninv.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Owner: Member 1 (Team Lead / Integration & Database Architect)
 *
 * Central connection pool for the whole application.
 * Every DAO should call DBConnection.getConnection() and close it
 * in a try-with-resources block — never keep a connection open longer
 * than one method call.
 *
 * Edit the four constants below to match your local PostgreSQL setup.
 */
public final class DBConnection {

    private static final String URL      = "jdbc:postgresql://localhost:5432/cleaninv";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "postgres";

    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setPoolName("CleanInvPool");
        dataSource = new HikariDataSource(config);
    }

    private DBConnection() {
        // utility class, no instances
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
