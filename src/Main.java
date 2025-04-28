import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Statistics tracker = new Statistics();

        Habit habit1 = new Habit("Ранкова зарядка");
        tracker.addHabit(habit1);

        Record record1 = new Record(habit1, LocalDate.now(), true);
        Record record2 = new Record(habit1, LocalDate.now().plusDays(1), false);
        Record record3 = new Record(habit1, LocalDate.now().plusDays(2), true);

        tracker.addRecord(record1);
        tracker.addRecord(record2);
        tracker.addRecord(record3);

        System.out.println("Записи для звички:");
        for (Record record : tracker.getRecordsForHabit(habit1.getName())) {
            System.out.println(record.getDate() + " - Виконано: " + record.isCompleted());
        }
        System.out.println("Рівень виконання звички '" + habit1.getName() + "': " + tracker.getCompletionRate(habit1.getName()) + "%");
    }
}