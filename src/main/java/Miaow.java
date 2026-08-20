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

        while (true) {
            String command = scanner.nextLine();

            System.out.println("____________________________________________________________");

            if (command.equals("bye")) {
                System.out.println("Miaow: Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________\n");
                break;
            } else {
                System.out.println("Miaow: " + command);
            }

            //System.out.println(" " + command);
            System.out.println(
                    "____________________________________________________________"
            );
        }
        //
    }
}
