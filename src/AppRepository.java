import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppRepository implements Repository {

    @Override
    public int getStartDate(DateRange range) {
        Calendar calendar = Calendar.getInstance();
        switch (range) {
            case LAST_MONTH:
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case THIS_MONTH:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case LAST_WEEK:
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case THIS_WEEK:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
        }
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    @Override
    public int getEndDate(DateRange range) {
        Calendar calendar = Calendar.getInstance();
        switch (range) {
            case LAST_MONTH:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case THIS_MONTH:
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case LAST_WEEK:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case THIS_WEEK:
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
        }
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    @Override
    public void addRecord(Record record) {
        String query = "INSERT INTO records (habit_id, timestamp, completed) VALUES ((SELECT id FROM habits WHERE name = ?), ?, ?);";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, record.getHabit().getName());
            pstmt.setInt(2, record.getTimestamp());
            pstmt.setBoolean(3, record.isCompleted());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getProgress(String habitName, DateRange range) {
        int startDate = getStartDate(range);
        int endDate = getEndDate(range);
        String query = "SELECT COUNT(*) AS total, SUM(CASE WHEN completed THEN 1 ELSE 0 END) AS completed " +
                "FROM records WHERE habit_id = (SELECT id FROM habits WHERE name = ?) AND timestamp BETWEEN ? AND ?;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, habitName);
            pstmt.setInt(2, startDate);
            pstmt.setInt(3, endDate);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                int completed = rs.getInt("completed");
                return total == 0 ? 0 : (completed * 100) / total;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getOverallProgress(String habitName) {
        String query = "SELECT COUNT(*) AS total, SUM(CASE WHEN completed THEN 1 ELSE 0 END) AS completed " +
                "FROM records WHERE habit_id = (SELECT id FROM habits WHERE name = ?);";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, habitName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                int completed = rs.getInt("completed");
                return total == 0 ? 0 : (completed * 100) / total;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Record> getRecordsForHabit(String habitName) {
        List<Record> records = new ArrayList<>();
        String query = "SELECT timestamp, completed FROM records WHERE habit_id = (SELECT id FROM habits WHERE name = ?);";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, habitName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                records.add(new Record(new Habit(habitName), rs.getInt("timestamp"), rs.getBoolean("completed")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    public void clearRecords() {
        String query = "DELETE FROM records;";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}