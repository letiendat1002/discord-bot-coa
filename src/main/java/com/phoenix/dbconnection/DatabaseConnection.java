package com.phoenix.dbconnection;

import com.phoenix.util.PropertyLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.phoenix.Main.LOGGER;

public class DatabaseConnection {
    private static Connection connection;

    static {
        PropertyLoader.loadProperties().ifPresentOrElse(props -> {
            var dbHost = props.getProperty("DB_HOST");
            var dbUsername = props.getProperty("DB_USERNAME");
            var dbPassword = props.getProperty("DB_PASSWORD");
            var dbName = props.getProperty("DB_NAME");
            var url = "jdbc:mysql://" + dbHost + "/" + dbName;

            try {
                connection = DatabaseConnection.getConnection(url, dbUsername, dbPassword);
            } catch (SQLException e) {
                LOGGER.error("Failed to connect to database. SQL State: {}, Error Code: {}",
                        e.getSQLState(), e.getErrorCode(), e);
            }
        }, () -> LOGGER.error("Failed to load properties"));
    }

    private DatabaseConnection() {
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to close database connection", e);
        }
    }

    private static Connection getConnection(String url,
                                            String username,
                                            String password)
            throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
