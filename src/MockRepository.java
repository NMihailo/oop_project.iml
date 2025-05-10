import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MockRepository implements Repository {
    private final List<Record> records;

    public MockRepository() {
        this.records = new ArrayList<>();
    }

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
        records.add(record);
    }

    @Override
    public int getProgress(String habitName, DateRange range) {
        int startDate = getStartDate(range);
        int endDate = getEndDate(range);
        int total = 0;
        int completed = 0;

        for (Record record : records) {
            if (record.getHabit().getName().equals(habitName)
                    && record.getTimestamp() >= startDate
                    && record.getTimestamp() <= endDate) {
                total++;
                if (record.isCompleted()) {
                    completed++;
                }
            }
        }

        return total == 0 ? 0 : (completed * 100) / total;
    }

    public int getOverallProgress(String habitName) {
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

