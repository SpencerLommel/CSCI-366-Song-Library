import java.sql.*;

public class DatabaseConnection {
    // this connection string is for a dev database and will only be valid until the end of May and then it will be deleted
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
