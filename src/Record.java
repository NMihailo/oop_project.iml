import java.time.LocalDate;

public class Record {
    private Habit habit;
    private LocalDate date;
    private boolean completed;

    public Record(Habit habit, LocalDate date, boolean completed) {
        this.habit = habit;
        this.date = date;
        this.completed = completed;
    }

    public Habit getHabit() {
        return habit;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}