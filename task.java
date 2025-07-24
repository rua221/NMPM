import org.json.simple.JSONObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String id;
    String title;
    String description;
    String dueDate;
    String priority;
    String status;
    String createdAt;
    String updatedAt;

    public Task(String title, String description, LocalDate dueDate, String priority) {
        this.id = String.valueOf(System.currentTimeMillis());
        this.title = title;
        this.description = description;
        this.dueDate = dueDate.format(FORMATTER);
        this.priority = priority;
        this.status = "Chưa hoàn thành";
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.updatedAt = this.createdAt;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("title", title);
        obj.put("description", description);
        obj.put("due_date", dueDate);
        obj.put("priority", priority);
        obj.put("status", status);
        obj.put("created_at", createdAt);
        obj.put("last_updated_at", updatedAt);
        return obj;
    }
}
