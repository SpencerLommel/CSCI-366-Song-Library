import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter table name to print: ");
        String tableName = scanner.nextLine();

        db.printTable(tableName);

        // test_user_table

//        String jdbcURL = "jdbc:postgresql://localhost:5432/simple_company";
//        String username = "replaceToYourUserName";
//        String password = "replaceToYourPassword";

//        try {
//            Class.forName("org.postgresql.Driver");
//            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
//        }    catch (ClassNotFoundException e) {
//            System.out.println("Cannot load driver");
//        }     catch (SQLException e) {
//        }

    }
}
