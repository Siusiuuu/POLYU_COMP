package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:oracle:thin:@studora.comp.polyu.edu.hk:1521:dbms"; // Database URL
    private static final String USER = "\"23087051d\""; // Replace with your MySQL username
    private static final String PASSWORD = "gqvxgllo"; // Replace with your MySQL password

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}