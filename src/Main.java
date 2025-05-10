import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.setupDatabase();

        AppRepository tracker = new AppRepository();

        Habit habit1 = new Habit("Ранкова зарядка");
        Habit habit2 = new Habit("Читання книги");
        Habit habit3 = new Habit("Пробіжка");

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT OR IGNORE INTO habits (name) VALUES (?);")) {
            pstmt.setString(1, habit1.getName());
            pstmt.executeUpdate();

            pstmt.setString(1, habit2.getName());
            pstmt.executeUpdate();

            pstmt.setString(1, habit3.getName());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        tracker.clearRecords();

        //Record record1 = new Record(habit3, (int) (System.currentTimeMillis() / 1000), true);
//        Record record2 = new Record(habit1, (int) (System.currentTimeMillis() / 1000 - 86400), false);
//        Record record3 = new Record(habit1, (int) (System.currentTimeMillis() / 1000 - 172800), true);

//        Record record4 = new Record(habit2, (int) (System.currentTimeMillis() / 1000), true);
//        Record record5 = new Record(habit2, (int) (System.currentTimeMillis() / 1000 - 345600), false);
//        Record record6 = new Record(habit2, (int) (System.currentTimeMillis() / 1000 - 432000), true);
//        Record record7 = new Record(habit1, (int) (System.currentTimeMillis() / 1000 - 1123200), true);

          //tracker.addRecord(record7);

        System.out.println("Записи для звички: " + habit1.getName());
        for (Record record : tracker.getRecordsForHabit(habit1.getName())) {
            System.out.println(record.convertSecondsToDate(record.getTimestamp()) + " - Виконано: " + record.isCompleted());
        }
        int completionRate = tracker.getProgress(habit1.getName(), DateRange.THIS_WEEK);
        System.out.println("Рівень виконання звички " + habit1.getName() + ": " + completionRate + "%");


        System.out.println("Записи для звички: " + habit2.getName());
        for (Record record : tracker.getRecordsForHabit(habit2.getName())) {
            System.out.println(record.convertSecondsToDate(record.getTimestamp()) + " - Виконано: " + record.isCompleted());
        }
        completionRate = tracker.getProgress(habit2.getName(), DateRange.LAST_WEEK);
        System.out.println("Рівень виконання звички " + habit2.getName() + ": " + completionRate + "%");

        System.out.println("Загальний рівень виконання звички " + habit1.getName() + ": " + tracker.getOverallProgress(habit1.getName()) + "%") ;
    }
}
