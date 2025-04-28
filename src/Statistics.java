import java.util.ArrayList;
import java.util.List;

public class Statistics implements HabitTrackerInterface {
    private List<Habit> habits;
    private List<Record> records;

    public Statistics() {
        this.habits = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    @Override
    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    @Override
    public void addRecord(Record record) {
        records.add(record);
    }

    @Override
    public int getCompletionRate(String habitName) {
        int total = 0;
        int completed = 0;

        for (Record record : records) {
            if (record.getHabit().getName().equals(habitName)) {
                total++;
                if (record.isCompleted()) {
                    completed++;
                }
            }
        }

        return total == 0 ? 0 : (completed * 100) / total;
    }

    @Override
    public List<Record> getRecordsForHabit(String habitName) {
        List<Record> habitRecords = new ArrayList<>();
        for (Record record : records) {
            if (record.getHabit().getName().equals(habitName)) {
                habitRecords.add(record);
            }
        }
        return habitRecords;
    }
}
