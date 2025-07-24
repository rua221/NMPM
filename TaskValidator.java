import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TaskValidator {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    public static LocalDate parseDueDate(String dueDateStr) throws DateTimeParseException {
        return LocalDate.parse(dueDateStr, FORMATTER);
    }

    public static boolean isValidPriority(String priority) {
        return priority.equals("Thấp") || priority.equals("Trung bình") || priority.equals("Cao");
    }

}


