import java.util.List;

public interface Repository {
    int getStartDate(DateRange range);
    int getEndDate(DateRange range);
    void addRecord(Record record);
    int getProgress(String habitName, DateRange range);
    List<Record> getRecordsForHabit(String habitName);
}

//void updateRecord(Record updatedRecord);
//void deleteRecord(Record recordToDelete);