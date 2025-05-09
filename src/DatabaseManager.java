import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:habits.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void setupDatabase() {
        String createHabitsTable = "CREATE TABLE IF NOT EXISTS habits ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT UNIQUE NOT NULL);";

        String createRecordsTable = "CREATE TABLE IF NOT EXISTS records ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "habit_id INTEGER NOT NULL, "
                + "timestamp DATETIME NOT NULL, "
                + "completed BOOLEAN NOT NULL, "
                + "FOREIGN KEY (habit_id) REFERENCES habits(id));";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createHabitsTable);
            stmt.execute(createRecordsTable);
            System.out.println("Таблиці створені або вже існують.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}