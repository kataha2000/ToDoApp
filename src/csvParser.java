import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class csvParser {
    public static List<Task> readFile() throws IOException {
        List<String> lines = processFile("./Tasks.csv");
        List<Task> Tasks = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\,");
            String title = parts[0];
            String description = parts[1];
            LocalDate date = LocalDate.parse(parts[2]);
            String status = parts[3];
            String category = parts[4];

            Task Task = new Task(title, description, date, status, category);
            Tasks.add(Task);
        }

        return Tasks;
    }

    public static List<String> processFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            createFile();
        }
        return lines;
    }

    public static void writeFile(List<Task> lines) {
        try {
            Writer w = new FileWriter("Tasks.csv");
            for (Task task : lines) {
                w.write(task.title + "," + task.description + "," + task.date + "," + task.status + "," + task.category + "\n");
            }
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile() {
        try {
            File myObj = new File("./Tasks.csv");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}