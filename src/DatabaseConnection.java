import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://postgres.spencerlommel.com:5432/csci-366-project";
    private static final String USER = "csci-366-project";
    private static final String PASSWORD = "csci-366-project";

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }


    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
