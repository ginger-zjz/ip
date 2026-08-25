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
                String content = command.substring(6);
                String[] parts = content.split(" /from | /to ", 3);
                Event event = new Event(parts[0]);
                event.from(parts[1]);
                event.to(parts[2]);
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
            System.out.println("Example: deadline return book /by Sunday");
            System.out.println("____________________________________________________________");

            } else if (command.equals("event")) {
                System.out.println("Miaow: Add task name, start time, and end time.");
                System.out.println("Example: event meeting /from Monday 2pm /to 4pm");
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
