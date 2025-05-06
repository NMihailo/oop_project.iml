import java.util.List;

public interface Repository {
    void addRecord(Record record);
    int getCompletionRate(String habitName);
    List<Record> getRecordsForHabit(String habitName);
    void updateRecord(Record updatedRecord);
    void deleteRecord(Record recordToDelete);
}
