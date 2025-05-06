import java.sql.Timestamp;
import java.util.Objects;

public class Record {
    private Habit habit;
    private Timestamp timestamp;
    private boolean completed;

    public Record(Habit habit, Timestamp timestamp, boolean completed) {
        this.habit = habit;
        this.timestamp = timestamp;
        this.completed = completed;
    }

    public Habit getHabit() {
        return habit;
    }

    public Timestamp getTimestamp() {
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
}