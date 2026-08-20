import java.util.Scanner;

public class Miaow {
    private static Task createTask(String command) {
        if (command.startsWith("todo ")) {
            String description = command.substring(5);
            return new Task(description);

        } else if (command.startsWith("deadline ")) {
            String content = command.substring(9);

            String[] parts = content.split(" /by ", 2);
            Deadline deadline = new Deadline(parts[0]);
            deadline.by(parts[1]);

            return deadline;

        } else if (command.startsWith("event ")) {
            String content = command.substring(6);

            String[] parts = content.split(" /from | /to ", 3);

            Event event = new Event(parts[0]);
            event.from(parts[1]);
            event.to(parts[2]);

            return event;
        }

        return null;
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

        Task[] items = new Task[100];
        int itemCount = 0;


        while (true) {
            String command = scanner.nextLine();

            System.out.println("____________________________________________________________");

            if (command.equals("bye")) {
                System.out.println("Miaow: Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________");
                break;
            } else if (command.equals("list")) {
                for (int i = 0; i < itemCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + items[i]);
                }
                System.out.println("____________________________________________________________");
            } else if (command.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(command.substring(5));
                items[taskNumber - 1].mark();

                System.out.println("Miaow: Marked task " + taskNumber);
                System.out.println("____________________________________________________________");
            } else if (command.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(command.substring(7));
                items[taskNumber - 1].unmark();

                System.out.println("Miaow: Unmarked task " + taskNumber);
                System.out.println("____________________________________________________________");
            } else {
            Task task = createTask(command);

            if (task != null) {
                items[itemCount] = task;
                itemCount++;

                System.out.println("Got it. I've added this task:");
                System.out.println(" " + task);
                System.out.println("Now you have " + itemCount + " tasks in the list.");
            } else {
                System.out.println("Miaow: That's not a task, try again!");
            }

            System.out.println("____________________________________________________________");
        }
        }
        //
    }
}
