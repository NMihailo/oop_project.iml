import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MockRepository implements Repository {
    private final List<Record> records = new ArrayList<>();

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

    @Override
    public void updateRecord(Record updatedRecord) {
        for (int i = 0; i < records.size(); i++) {
            Record record = records.get(i);
            if (record.equals(updatedRecord)) {
                records.set(i, updatedRecord);
                return;
            }
        }
    }

    @Override
    public void deleteRecord(Record recordToDelete) {
        Iterator<Record> iterator = records.iterator();
        while (iterator.hasNext()) {
            Record record = iterator.next();
            if (record.equals(recordToDelete)) {
                iterator.remove();
                return;
            }
        }
    }
}

