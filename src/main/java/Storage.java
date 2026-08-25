
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Storage {
    private static final String FILE_PATH = "./data/miaow.txt";

    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            // Create directories if they don't exist
            Path path = Paths.get("./data");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Created data directory: ./data/");
            }

            // Write tasks to file
            FileWriter writer = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Check if file exists
        if (!file.exists()) {
            System.out.println("No existing data file found. Starting with empty task list.");
            return tasks;
        }

        try {
            java.util.Scanner fileScanner = new java.util.Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Task task = parseTaskFromLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
            fileScanner.close();
            System.out.println("Loaded " + tasks.size() + " tasks from file.");
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Error loading tasks: File not found.");
        }

        return tasks;
    }

    private static Task parseTaskFromLine(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        try {
            switch (type) {
                case "T":
                    // Todo: T | 0/1 | description
                    Task task = new Task(description);
                    if (isDone) task.mark();
                    return task;

                case "D":
                    // Deadline: D | 0/1 | description | by
                    if (parts.length < 4) return null;
                    Deadline deadline = new Deadline(description);
                    deadline.by(parts[3]);
                    if (isDone) deadline.mark();
                    return deadline;

                case "E":
                    // Event: E | 0/1 | description | from | to
                    if (parts.length < 5) return null;
                    Event event = new Event(description);
                    event.from(parts[3]);
                    event.to(parts[4]);
                    if (isDone) event.mark();
                    return event;

                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
