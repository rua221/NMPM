public class Main {
    public static void main(String[] args) {
        PersonalTaskManager manager = new PersonalTaskManager();

        JSONObject task = manager.addTask("Học Java", "Ôn tập OOP", "2025-07-27", "Cao");
        if (task != null) {
            String id = (String) task.get("id");

            manager.updateTask(id, "Học lại Java nâng cao", "Trung bình", "Chưa hoàn thành");

            manager.filterByStatus("Chưa hoàn thành");
            manager.filterByDueDate("2025-07-27");
            manager.showTasksThisWeek();

            manager.deleteTask(id);
        }
    }
}
