import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

public class Record {
    private final Habit habit;
    private final int timestamp;
    private final boolean completed;

    public Record(Habit habit, int timestamp, boolean completed) {
        this.habit = habit;
        this.timestamp = timestamp;
        this.completed = completed;
    }

    public Habit getHabit() {
        return habit;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return completed == record.completed && Objects.equals(habit, record.habit) && Objects.equals(timestamp, record.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habit, timestamp, completed);
    }

    @Override
    public String toString() {
        return "Запис: " + habit +
                ", Дата= " + timestamp +
                ", Виконано= " + completed;
    }

    public LocalDate convertSecondsToDate(int timestamp) {
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}