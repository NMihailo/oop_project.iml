import java.sql.Timestamp;

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

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}