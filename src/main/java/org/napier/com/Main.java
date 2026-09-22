package org.napier.com;

import org.napier.com.database.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();

        // Connect to the database
        db.connect();

        if (db.getConnection() != null) {
            System.out.println("Main: database connection ready.");
        } else {
            System.out.println("Main: no database connection.");
        }

        // Disconnect from the database
        db.disconnect();
    }
}
