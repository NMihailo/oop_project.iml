import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Statistics {
    private final List<Record> records;

    public Statistics() {
        this.records = new ArrayList<>();
    }

    private Timestamp getStartDate(DateRange range) {
        Calendar calendar = Calendar.getInstance();
        switch (range) {
            case Last_month:
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case This_month:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case Last_week:
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case This_week:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
        }
        return new Timestamp(calendar.getTimeInMillis());
    }

    private Timestamp getEndDate(DateRange range) {
        Calendar calendar = Calendar.getInstance();
        switch (range) {
            case Last_month:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case This_month:
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case Last_week:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case This_week:
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
        }
        return new Timestamp(calendar.getTimeInMillis());
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public int getCompletionRate(String habitName, DateRange range) {
        Timestamp startDate = getStartDate(range);
        Timestamp endDate = getEndDate(range);
        int total = 0;
        int completed = 0;

        for (Record record : records) {
            if (record.getHabit().getName().equals(habitName)
                    && record.getTimestamp().after(startDate)
                    && record.getTimestamp().before(endDate)) {
                total++;
                if (record.isCompleted()) {
                    completed++;
                }
            }
        }

        return total == 0 ? 0 : (completed * 100) / total;
    }

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

