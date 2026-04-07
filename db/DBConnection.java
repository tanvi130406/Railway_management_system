package db;

import java.sql.Connection;
import java.sql.DriverManager;
//estabilished jdbc connection
public class DBConnection {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mini_irctc",
                "root",
                "your root password here"
            );

            return conn;

        } catch (Exception e) {//handles driver loading and connection failure
            System.out.println("Database Connection Error!");
            e.printStackTrace();
            return null;
        }
    }
}

