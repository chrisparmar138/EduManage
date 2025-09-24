package com.edumanage.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A utility class to manage the database connection.
 * This class provides a single, static method to get a connection to the PostgreSQL database.
 */
public class DatabaseUtil {

    // --- Database Connection Details ---
    // The URL for connecting to the PostgreSQL database.
    // Format: jdbc:postgresql://<host>:<port>/<databaseName>
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/edumanage_db";

    // IMPORTANT: Replace these with your actual PostgreSQL username and password.
    private static final String USER = "postgres";
    private static final String PASS = "*****";

    /**
     * Attempts to establish a connection with the database.
     * @return A Connection object to the database.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // This step is often not needed for modern JDBC drivers,
            // but it's good practice to ensure the driver is loaded.
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found. Include it in your library path.");
            e.printStackTrace();
            // Re-throw as an SQLException to be handled by the caller
            throw new SQLException("Database driver not found.", e);
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}

