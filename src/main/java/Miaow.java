import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Miaow {
    private static Task createTask(String command) {
            if (command.startsWith("todo")) {
                String description = command.substring(5).trim();
                if (description.isEmpty()) {
                    return null;
                }
                return new Task(description);
            } else if (command.startsWith("deadline")) {
                String content = command.substring(9).trim();
                String[] parts = content.split(" /by ", 2);
                if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                    return null;
                }
                Deadline deadline = new Deadline(parts[0].trim());
                deadline.by(parts[1].trim());
                return deadline;
            } else if (command.startsWith("event")) {
                String content = command.substring(6).trim();
                String[] parts = content.split(" /from | /to ", 3);
                if (parts.length < 3 || parts[0].trim().isEmpty() ||
                        parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
                    return null;
                }
                Event event = new Event(parts[0].trim());
                event.from(parts[1].trim());
                event.to(parts[2].trim());
                return event;
            }
        return null;
    }
    private static boolean isValidTaskNumber(String command, String prefix, int itemCount) {
        try {
            int taskNumber = Integer.parseInt(command.substring(prefix.length()).trim());
            return taskNumber >= 1 && taskNumber <= itemCount;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidDate(String dateStr) {
        try {
            // Try yyyy-MM-dd
            java.time.LocalDate.parse(dateStr);
            return true;
        } catch (java.time.format.DateTimeParseException e1) {
            try {
                // Try dd/MM/yyyy
                java.time.format.DateTimeFormatter formatter =
                        java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy");
                java.time.LocalDate.parse(dateStr, formatter);
                return true;
            } catch (java.time.format.DateTimeParseException e2) {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        String banner = " _._     _,-'\"\"`-._\n" +
                "(,-.`._,'(       |\\`-/|\n" +
                "    `-.-' \\ )-`( , o o)    MIAOW\n" +
                "          `-    \\`_`\"'-\n" +
                "Hello! I'm Miaow.\n" +
                "What can I do for you?\n" +
                "____________________________________________________________";
        System.out.println(banner);

        Scanner scanner = new Scanner(System.in);

        //ArrayList<Task> items = new ArrayList<>();
        //int itemCount = 0;

        // Load tasks from file if exists
        ArrayList<Task> items = Storage.loadTasks();

        if (items.size() > 0) {
            System.out.println("loaded " + items.size() + " existing tasks.");
            System.out.println("____________________________________________________________");
        }


        while (true) {
            String command = scanner.nextLine();

            System.out.println("____________________________________________________________");

            if (command.equals("bye")) {
                Storage.saveTasks(items);
                System.out.println("Miaow: Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________");
                break;
            } else if (command.equals("list")) {
                for (int i = 0; i < items.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + items.get(i));
                }
                System.out.println("____________________________________________________________");
            } else if (command.startsWith("delete ")) {
                if (!isValidTaskNumber(command, "delete ", items.size())) {
                    System.out.println("Miaow: Invalid task number.");
                } else {
                    int taskNumber = Integer.parseInt(command.substring(7).trim());
                    //items.get(taskNumber - 1).mark();
                    Task deletedTask = items.remove(taskNumber - 1);

                    System.out.println("Miaow: Deleted task:");
                    System.out.println(" " + deletedTask);
                    System.out.println("Now you have " + items.size() + " tasks in the list.");

                    //save
                    Storage.saveTasks(items);
                }
                System.out.println("____________________________________________________________");
            } else if (command.startsWith("mark ")) {
                if (!isValidTaskNumber(command, "mark ", items.size())) {
                    System.out.println("Miaow: Invalid task number.");
                } else {
                    int taskNumber = Integer.parseInt(command.substring(5));
                    items.get(taskNumber - 1).mark();

                    System.out.println("Miaow: Marked task " + taskNumber);

                    //save
                    Storage.saveTasks(items);
                }
                System.out.println("____________________________________________________________");
            } else if (command.startsWith("unmark ")) {
                if (!isValidTaskNumber(command, "unmark ", items.size())) {
                    System.out.println("Miaow: Invalid task number.");
                } else {
                    int taskNumber = Integer.parseInt(command.substring(7).trim());
                    items.get(taskNumber - 1).unmark();

                    System.out.println("Miaow: Unmarked task " + taskNumber);

                    //save
                    Storage.saveTasks(items);
                }

                System.out.println("____________________________________________________________");
            }  else if (command.equals("todo")) {
            System.out.println("Miaow: Add task name.");
            System.out.println("____________________________________________________________");

            } else if (command.equals("deadline")) {
                System.out.println("Miaow: Add task name and deadline.");
                System.out.println("Example: deadline return book /by 2019-12-02");
                System.out.println("Example: deadline return book /by 2/12/2019");
                System.out.println("Example: deadline return book /by 2/12/2019 1800");
                System.out.println("____________________________________________________________");

            } else if (command.equals("event")) {
                System.out.println("Miaow: Add task name, start time, and end time.");
                System.out.println("Example: event meeting /from 2019-08-06 14:00 /to 16:00");
                System.out.println("Example: event meeting /from 6/8/2019 1400 /to 1600");
                System.out.println("____________________________________________________________");

            } else {
            Task task = createTask(command);

            if (task != null) {
                items.add(task);
                //itemCount++;

                System.out.println("Got it. I've added this task:");
                System.out.println(" " + task);
                System.out.println("Now you have " + items.size() + " tasks in the list.");

                //save
                Storage.saveTasks(items);
            } else {
                System.out.println("Miaow: That's not a command, try again!");
            }

            System.out.println("____________________________________________________________");
        }
        }

    }
}
