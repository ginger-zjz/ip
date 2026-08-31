package miaow;

import java.util.ArrayList;
import miaow.storage.Storage;
import miaow.storage.TaskList;
import miaow.task.Task;
import miaow.user.Parser;
import miaow.user.UI;


/**
 * Public class miaow
 */
public class Miaow {
    private Storage storage;
    private Parser parser;
    private TaskList tasks;
    private UI ui;

    enum CommandType {
        BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, FIND, UNKNOWN, INVALID_TODO, INVALID_DEADLINE,
        INVALID_EVENT
    }

    /**
     * Constructor for class
     */
    public Miaow() {
        this.ui = new UI();
        this.parser = new Parser();
        this.storage = new Storage("./data/miaow.txt");
//        try {
//            tasks = new TaskList(storage.load());
//        } catch (MiaowException e) {
//            ui.showLoadingError();
//            tasks = new TaskList();
//        }
        ArrayList<Task> loadedTasks = storage.loadTasks();
        this.tasks = new TaskList(loadedTasks);

        ui.showLoadingSuccess(loadedTasks.size());
    }

    /**
     * Runs the method
     */
    public void run() {
        ui.showWelcome();
        while (true) {
            String command = ui.readCommand();
            ui.showLine();

            try {
                Parser.CommandType commandType = parser.getCommandType(command);

                switch (commandType) {
                    case BYE:
                        ui.showGoodbye();
                        storage.saveTasks(tasks.getTasks());
                        return;
                    case LIST:
                        if (tasks.isEmpty()) {
                            ui.showEmptyTaskList();
                        } else {
                            ui.showTaskList(tasks.getTasks());
                        }
                        break;
                    case TODO:
                        Task todo = parser.parseTodo(command);
                        if (todo != null) {
                            tasks.addTask(todo);
                            ui.showTaskAdded(todo, tasks.size());
                            storage.saveTasks(tasks.getTasks());
                        } else {
                            ui.showError("Invalid todo format");
                        }
                        break;
                    case DEADLINE:
                        Task deadline = parser.parseDeadline(command);
                        if (deadline != null) {
                            tasks.addTask(deadline);
                            ui.showTaskAdded(deadline, tasks.size());
                            storage.saveTasks(tasks.getTasks());
                        } else {
                            ui.showDeadlineHelp();
                        }
                        break;
                    case EVENT:
                        Task event = parser.parseEvent(command);
                        if (event != null) {
                            tasks.addTask(event);
                            ui.showTaskAdded(event, tasks.size());
                            storage.saveTasks(tasks.getTasks());
                        } else {
                            ui.showEventHelp();
                        }
                        break;

                    case MARK:
                        int markIndex = parser.parseTaskNumber(command, "mark ");
                        if (tasks.isValidIndex(markIndex)) {
                            tasks.markTask(markIndex);
                            ui.showMarked(tasks.getTask(markIndex), markIndex + 1);
                            storage.saveTasks(tasks.getTasks());
                        } else {
                            ui.showInvalidTaskNumber();
                        }
                        break;

                    case UNMARK:
                        int unmarkIndex = parser.parseTaskNumber(command, "unmark ");
                        if (tasks.isValidIndex(unmarkIndex)) {
                            tasks.unmarkTask(unmarkIndex);
                            ui.showUnmarked(tasks.getTask(unmarkIndex), unmarkIndex + 1);
                            storage.saveTasks(tasks.getTasks());
                        } else {
                            ui.showInvalidTaskNumber();
                        }
                        break;

                    case DELETE:
                        int deleteIndex = parser.parseTaskNumber(command, "delete ");
                        if (tasks.isValidIndex(deleteIndex)) {
                            Task deletedTask = tasks.deleteTask(deleteIndex);
                            ui.showTaskDeleted(deletedTask, tasks.size());
                            storage.saveTasks(tasks.getTasks());
                        } else {
                            ui.showInvalidTaskNumber();
                        }
                        break;

                    case FIND:
                        String keyword = parser.parseFindKeyword(command);

                        if (keyword == null) {
                            ui.showMessage("Please provide a keyword to search for.");
                        } else {
                            ArrayList<Task> matchingTasks =
                                    tasks.findTasksByKeyword(keyword);
                            ui.showFindResults(keyword, matchingTasks);
                        }
                        break;
                }
            } catch (Exception e) {
                ui.showError("Miaow: An error occurred -- " + e.getMessage());
            }

            ui.showLine();
        }
    }

    public static void main(String[] args) {
        new Miaow().run();
    }
}
