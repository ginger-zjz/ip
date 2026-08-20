import java.util.Scanner;

public class Miaow {
    public static void main(String[] args) {
        String banner = " _._     _,-'\"\"`-._\n" +
                "(,-.`._,'(       |\\`-/|\n" +
                "    `-.-' \\ )-`( , o o)    MIAOW\n" +
                "          `-    \\`_`\"'-\n" +
                "Hello! I'm Miaow.\n" +
                "What can I do for you?\n" +
                "____________________________________________________________\n";
        System.out.println(banner);


        Scanner scanner = new Scanner(System.in);

        String[] items = new String[100];
        int itemCount = 0;


        while (true) {
            String command = scanner.nextLine();

            System.out.println("____________________________________________________________");

            if (command.equals("bye")) {
                System.out.println("Miaow: Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________\n");
                break;
            } else if (command.equals("list")) {
                for (int i = 0; i < itemCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + items[i]);
                }
                System.out.println("____________________________________________________________");
            }
            else {
                items[itemCount] = command;
                itemCount++;

                System.out.println("Miaow added: " + command);
                System.out.println("____________________________________________________________");
            }
        }
        //
    }
}
