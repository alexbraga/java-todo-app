package util;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionFactory {
    static Dotenv dotenv = Dotenv.load();
    public static final String DRIVER = dotenv.get("DB_DRIVER");
    public static final String URL = dotenv.get("DB_URL");
    public static final String USER = dotenv.get("DB_USER");
    public static final String PASS = dotenv.get("DB_PASSWORD");
    public static Connection conn;

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
            return conn;
        } catch (Exception ex) {
            // handle any errors
            throw new RuntimeException("SQL Connection error: " + ex.getMessage());
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException("SQL Close Connection error: " + ex.getMessage());
        }
    }

    public static void closeConnection(Connection conn, PreparedStatement statement) {
        try {
            if (conn != null) {
                conn.close();
            }

            if (statement != null) {
                statement.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException("SQL Close Connection error: " + ex.getMessage());
        }
    }

    public static void closeConnection(Connection conn, PreparedStatement statement, ResultSet result) {
        try {
            if (conn != null) {
                conn.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (result != null) {
                result.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException("SQL Close Connection error: " + ex.getMessage());
        }
    }
}
