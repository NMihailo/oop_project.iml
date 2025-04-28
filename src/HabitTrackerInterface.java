import java.util.List;

public interface HabitTrackerInterface {
    void addHabit(Habit habit);
    void addRecord(Record record);
    int getCompletionRate(String habitName);
    List<Record> getRecordsForHabit(String habitName);
}
