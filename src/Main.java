import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.setupDatabase();
        AppRepository tracker = new AppRepository();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1) Додати звичку");
            System.out.println("2) Додати виконання сьогодні");
            System.out.println("3) Перегляд виконання");
            System.out.println("   а) За цей тиждень");
            System.out.println("   б) За минулий тиждень");
            System.out.println("   в) За цей місяць");
            System.out.println("   г) За минулий місяць");
            System.out.println("   д) За весь час");
            System.out.println("4) Очищення таблиці записів");
            System.out.println("5) Вихід");
            System.out.print("Оберіть опцію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введіть назву звички: ");
                    String habitName = scanner.nextLine();
                    Habit habit = new Habit(habitName);
                    try (Connection conn = DatabaseManager.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement("INSERT OR IGNORE INTO habits (name) VALUES (?);")) {
                        pstmt.setString(1, habit.getName());
                        pstmt.executeUpdate();
                        System.out.println("Звичка '" + habit.getName() + "' додана!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "2":
                    System.out.print("Введіть назву звички, яку хочете позначити: ");
                    String habitToTrack = scanner.nextLine();
                    Habit trackedHabit = new Habit(habitToTrack);
                    System.out.print("Чи виконано звичку сьогодні? (+/-): ");
                    String completionInput = scanner.nextLine().trim();

                    boolean isCompleted;
                    if (completionInput.equals("+")) {
                        isCompleted = true;
                    } else if (completionInput.equals("-")) {
                        isCompleted = false;
                    } else {
                        System.out.println("Некоректне введення! Використовуйте '+' для виконано або '-' для не виконано.");
                        continue;
                    }

                    Record newRecord = new Record(trackedHabit, (int) (System.currentTimeMillis() / 1000), isCompleted);
                    tracker.addRecord(newRecord);

                    System.out.println("Запис додано: " + habitToTrack + " - Виконано: " + isCompleted);
                    break;

                case "3":
                    System.out.print("Введіть назву звички для перегляду виконання: ");
                    String habitToView = scanner.nextLine();
                    System.out.print("Оберіть період (а/б/в/г/д): ");
                    String periodChoice = scanner.nextLine();

                    DateRange range = null;
                    switch (periodChoice) {
                        case "а":
                            range = DateRange.THIS_WEEK;
                            break;
                        case "б":
                            range = DateRange.LAST_WEEK;
                            break;
                        case "в":
                            range = DateRange.THIS_MONTH;
                            break;
                        case "г":
                            range = DateRange.LAST_MONTH;
                            break;
                        case "д":
                            System.out.println("Записи для звички: " + habitToView);
                            for (Record record : tracker.getRecordsForHabit(habitToView)) {
                                System.out.println(record.convertSecondsToDate(record.getTimestamp()) + " - Виконано: " + record.isCompleted());
                            }
                            int overallCompletionRate = tracker.getOverallProgress(habitToView);
                            System.out.println("Загальний рівень виконання звички " + habitToView + ": " + overallCompletionRate + "%");
                            break;
                        default:
                            System.out.println("Некоректний вибір періоду!");
                            continue;
                    }

                    if (range != null) {
                        System.out.println("Записи для звички: " + habitToView);
                        for (Record record : tracker.getRecordsForHabit(habitToView)) {
                            System.out.println(record.convertSecondsToDate(record.getTimestamp()) + " - Виконано: " + record.isCompleted());
                        }
                        int completionRate = tracker.getProgress(habitToView, range);
                        System.out.println("Рівень виконання звички " + habitToView + ": " + completionRate + "%");
                    }
                    break;

                case "4":
                    System.out.print("Ви впевнені, що хочете очистити таблицю записів? (так/ні): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("так")) {
                        tracker.clearRecords();
                        System.out.println("Таблицю записів очищено!");
                    } else {
                        System.out.println("Очищення скасовано.");
                    }
                    break;

                case "5":
                    System.out.println("До побачення!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невірний вибір, спробуйте ще раз.");
            }
        }
    }
}