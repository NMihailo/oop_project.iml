import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AppRepository {
    public void addHabit(String name) {
        String sql = "INSERT INTO habits (name) VALUES (?);";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Звичка додана: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<String> getHabits() {
        List<String> habits = new ArrayList<>();
        String sql = "SELECT name FROM habits;";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                habits.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return habits;
    }

    public void addRecord(String habitName, Timestamp timestamp, boolean completed) {
        String habitQuery = "SELECT id FROM habits WHERE name = ?;";
        String insertRecord = "INSERT INTO records (habit_id, timestamp, completed) VALUES (?, ?, ?);";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement habitStmt = conn.prepareStatement(habitQuery)) {
            habitStmt.setString(1, habitName);
            ResultSet rs = habitStmt.executeQuery();

            if (rs.next()) {
                int habitId = rs.getInt("id");

                try (PreparedStatement recordStmt = conn.prepareStatement(insertRecord)) {
                    recordStmt.setInt(1, habitId);
                    recordStmt.setTimestamp(2, timestamp);
                    recordStmt.setBoolean(3, completed);
                    recordStmt.executeUpdate();
                    System.out.println("Запис додано: " + habitName);
                }
            } else {
                System.out.println("Звичку не знайдено.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}