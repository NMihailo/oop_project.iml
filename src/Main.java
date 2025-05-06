import java.time.LocalDateTime;
import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {
        Statistics tracker = new Statistics();

        Habit habit1 = new Habit("Ранкова зарядка");
        Habit habit2 = new Habit("Читання книги");

        Record record1 = new Record(habit1, Timestamp.valueOf(LocalDateTime.now()), true);
        Record record2 = new Record(habit1, Timestamp.valueOf(LocalDateTime.now().minusDays(1)), false);
        Record record3 = new Record(habit1, Timestamp.valueOf(LocalDateTime.now().minusDays(2)), true);

        Record record4 = new Record(habit2, Timestamp.valueOf(LocalDateTime.now()), true);
        Record record5 = new Record(habit2, Timestamp.valueOf(LocalDateTime.now().minusDays(4)), true);
        Record record6 = new Record(habit2, Timestamp.valueOf(LocalDateTime.now().minusDays(5)), true);
        Record record7 = new Record(habit2, Timestamp.valueOf(LocalDateTime.now().minusDays(13)), false);

        tracker.addRecord(record1);
        tracker.addRecord(record2);
        tracker.addRecord(record3);
        tracker.addRecord(record4);
        tracker.addRecord(record5);
        tracker.addRecord(record6);
        tracker.addRecord(record7);

        System.out.println("Записи для звички:");
        for (Record record : tracker.getRecordsForHabit(habit1.getName())) {
            System.out.println(record.getTimestamp() + " - Виконано: " + record.isCompleted());
        }
        int completionRate = tracker.getCompletionRate(habit1.getName(), DateRange.This_week);
        System.out.println("Рівень виконання звички "+ habit1.getName() + ": " + completionRate + "%");


        System.out.println("Записи для звички:");
        for (Record record : tracker.getRecordsForHabit(habit2.getName())) {
            System.out.println(record.getTimestamp() + " - Виконано: " + record.isCompleted());
        }
        completionRate = tracker.getCompletionRate(habit2.getName(), DateRange.Last_month);
        System.out.println("Рівень виконання звички "+ habit2.getName() + ": " + completionRate + "%");

        System.out.println(habit1);
        System.out.println(record1);
    }
}