package org.napier.com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles the MySQL connection.
 * Reads config from environment so the same jar works
 * locally (localhost) and in Docker Compose (host=mysql).
 */
public class DatabaseConnection {
    private Connection connection;

    /**
     * Connect with retries. MySQL can still be starting
     * even after the healthcheck passes, so we try 10 times.
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
                System.out.println("Successfully connected to " + host + ":" + port + "/" + dbName);
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

    public Connection getConnection() {
        return connection;
    }

    private String getEnvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
