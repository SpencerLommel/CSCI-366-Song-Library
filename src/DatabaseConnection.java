import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://postgres.spencerlommel.com:5432/csci-366-project";
    private static final String USER = "csci-366-project";
    private static final String PASSWORD = "csci-366-project";

    private Connection connection;

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printTable(String tableName) {
        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            // Print column headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-20s", meta.getColumnName(i));
            }
            System.out.println();

            // Print separator
            for (int i = 1; i <= columnCount; i++) {
                System.out.print("--------------------");
            }
            System.out.println();

            // Print rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-20s", rs.getString(i));
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
