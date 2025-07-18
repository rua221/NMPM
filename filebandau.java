// Task.java
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

// TaskValidator.java
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

// TaskRepository.java
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

public class TaskRepository {
    private static final String DB_FILE_PATH = "tasks_database.json";

    static {
        File file = new File(DB_FILE_PATH);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]");
            } catch (IOException e) {
                System.err.println("Không thể tạo file database: " + e.getMessage());
            }
        }
    }

    public static JSONArray load() {
        try (FileReader reader = new FileReader(DB_FILE_PATH)) {
            return (JSONArray) new JSONParser().parse(reader);
        } catch (IOException | ParseException e) {
            System.err.println("Lỗi đọc file: " + e.getMessage());
            return new JSONArray();
}
    }

    public static void save(JSONArray data) {
        try (FileWriter writer = new FileWriter(DB_FILE_PATH)) {
            writer.write(data.toJSONString());
        } catch (IOException e) {
            System.err.println("Lỗi ghi file: " + e.getMessage());
        }
    }
}

// PersonalTaskManager.java
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PersonalTaskManager {

    public JSONObject addTask(String title, String desc, String dueDateStr, String priority) {
        if (!TaskValidator.isValidTitle(title)) {
            System.out.println("Lỗi: Tiêu đề không được để trống.");
            return null;
        }

        LocalDate dueDate;
        try {
            dueDate = TaskValidator.parseDueDate(dueDateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Lỗi định dạng ngày.");
            return null;
        }

        if (!TaskValidator.isValidPriority(priority)) {
            System.out.println("Lỗi mức độ ưu tiên.");
            return null;
        }

        JSONArray tasks = TaskRepository.load();
        for (Object obj : tasks) {
            JSONObject task = (JSONObject) obj;
            if (task.get("title").equals(title) && task.get("due_date").equals(dueDateStr)) {
                System.out.println("Trùng lặp nhiệm vụ.");
                return null;
            }
        }

        Task newTask = new Task(title, desc, dueDate, priority);
        tasks.add(newTask.toJson());
        TaskRepository.save(tasks);
        System.out.println("Đã thêm nhiệm vụ.");
        return newTask.toJson();
    }

    public static void main(String[] args) {
        PersonalTaskManager manager = new PersonalTaskManager();
        manager.addTask("Mua sách", "Java", "2025-07-20", "Cao");
        manager.addTask("", "Không tiêu đề", "2025-07-20", "Thấp");
    }
}
