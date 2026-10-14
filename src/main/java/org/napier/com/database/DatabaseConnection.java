package org.napier.com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages the application's connection to the MySQL world database.
 * Configuration is read from environment variables so the same JAR can run
 * locally and through Docker Compose.
 */
public class DatabaseConnection {
    private Connection connection;

    /**
     * Opens the database connection, retrying while MySQL finishes starting.
     * The connection remains {@code null} if all attempts fail.
     */
    public void connect() {
        String host = getEnvOrDefault("DB_HOST", "localhost");
        String port = getEnvOrDefault("DB_PORT", "3306");
        String dbName = getEnvOrDefault("DB_NAME", "world");
        String user = getEnvOrDefault("DB_USER", "devops");
        String password = getEnvOrDefault("DB_PASSWORD", "devopspassword");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";

        int retries = 10;
        for (int i = 0; i < retries; i++) {
            System.out.println("Connecting to database (attempt " + (i + 1) + "/" + retries + ")...");
            try {
                // Short wait before each retry (except first) to let MySQL finish starting
                if (i > 0) {
                    Thread.sleep(5000);
                }
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Successfully connected to " + host + ":" + port + "/" + dbName + "\n");
                return;
            } catch (SQLException e) {
                System.out.println("Failed to connect attempt " + (i + 1) + ": " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Connection retry interrupted");
                return;
            }
        }
        System.out.println("Could not connect to database after " + retries + " attempts.");
    }

    /**
     * Closes the active database connection and clears the stored reference.
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from database.");
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }

    /**
     * Returns the current database connection.
     *
     * @return active connection, or {@code null} when not connected
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Reads a non-blank environment variable or returns its fallback value.
     *
     * @return configured value or the supplied fallback
     */
    private String getEnvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
