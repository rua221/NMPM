import java.time.LocalDate;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PersonalTaskManager {

    public JSONObject addTask(String title, String desc, String dueDateStr, String priority) {
        if (!TaskValidator.isValid(title, desc, dueDateStr, priority)) return null;

        LocalDate dueDate = LocalDate.parse(dueDateStr);
        Task task = new Task(title, desc, dueDate, priority);
        JSONArray tasks = TaskRepository.load();
        tasks.add(task.toJSON());
        TaskRepository.save(tasks);
        return task.toJSON();
    }

    public void deleteTask(String id) {
        JSONArray tasks = TaskRepository.load();
        tasks.removeIf(obj -> ((JSONObject) obj).get("id").equals(id));
        TaskRepository.save(tasks);
    }

    public void updateTask(String id, String newTitle, String newPriority, String newStatus) {
        JSONArray tasks = TaskRepository.load();
        for (Object obj : tasks) {
            JSONObject task = (JSONObject) obj;
            if (task.get("id").equals(id)) {
                task.put("title", newTitle);
                task.put("priority", newPriority);
                task.put("status", newStatus);
            }
        }
        TaskRepository.save(tasks);
    }

    public void filterByStatus(String status) {
        JSONArray tasks = TaskRepository.load();
        System.out.println("Nhiệm vụ có trạng thái: " + status);
        for (Object obj : tasks) {
            JSONObject task = (JSONObject) obj;
            if (task.get("status").equals(status)) {
                System.out.println("- " + task.get("title"));
            }
        }
    }

    public void filterByDueDate(String date) {
        JSONArray tasks = TaskRepository.load();
        System.out.println("Nhiệm vụ có hạn chót: " + date);
        for (Object obj : tasks) {
            JSONObject task = (JSONObject) obj;
            if (task.get("due_date").equals(date)) {
                System.out.println("- " + task.get("title"));
            }
        }
    }

    public void showTasksThisWeek() {
        JSONArray tasks = TaskRepository.load();
        LocalDate now = LocalDate.now();
        LocalDate end = now.plusDays(7);

        System.out.println("Nhiệm vụ trong 7 ngày tới:");
        for (Object obj : tasks) {
            JSONObject task = (JSONObject) obj;
            LocalDate d = LocalDate.parse((String) task.get("due_date"));
            if (!d.isBefore(now) && !d.isAfter(end)) {
                System.out.println("- " + task.get("title") + " (Hạn: " + d + ")");
            }
        }
    }
}
