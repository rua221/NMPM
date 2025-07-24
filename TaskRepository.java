import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TaskRepository {
    private static final String FILE_PATH = "tasks.json";

    public static void save(JSONArray tasks) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(tasks.toJSONString());
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }

    public static JSONArray load() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            return (JSONArray) parser.parse(reader);
        } catch (IOException | ParseException e) {
            return new JSONArray();
        }
    }
}
