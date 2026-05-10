package com.farmtracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static final String URL = System.getenv().getOrDefault(
            "FARMTRACKER_DB_URL",
            "jdbc:mysql://localhost:3306/farmtracker?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    private static final String USERNAME = System.getenv().getOrDefault("FARMTRACKER_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("FARMTRACKER_DB_PASSWORD", "root");

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            throw new SQLException("MySQL JDBC driver was not found.", exception);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
