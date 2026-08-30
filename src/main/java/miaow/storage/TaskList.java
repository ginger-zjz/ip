package miaow.storage;

import miaow.task.Deadline;
import miaow.task.Event;
import miaow.task.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks != null ? tasks : new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        return tasks.remove(index);
    }

    public void markTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        tasks.get(index).mark();
    }

    public void unmarkTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        tasks.get(index).unmark();
    }

    public Task getTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    //public ArrayList<Task> findTasksByKeyword(String keyword) {}

    //public ArrayList<Deadline> getDeadlinesOnDate(LocalDate date) {}

    //public ArrayList<Event> getEventsOnDate(LocalDate date) {}

    public boolean isValidIndex(int index) {
        return index <= tasks.size() && index >= 0;
    }

    public void clear() {
        this.tasks.clear();
    }

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

    /**
     * finds the list of tasks using a keyword
     * @param keyword The keyword
     * @return a list of tasks
     */
    public ArrayList<Task> findTasksByKeyword(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            return matchingTasks;
        }

        String searchTerm = keyword.trim().toLowerCase();

        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(searchTerm)) {
                matchingTasks.add(task);
            }
        }

        return matchingTasks;
    }
}
