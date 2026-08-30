package miaow.task;

public class Task {
    String taskName;
    boolean marked;

    public Task(String name) {
        this.taskName = name;
        this.marked = false;
    }

    /**
     * Returns marked status of task.
     */
    public boolean isDone() {
        return this.marked;
    }

    /**
     * Returns name of task.
     */
    public String getDescription() {
        return this.taskName;
    }

    /**
     * Marks a task as done.
     */
    public void mark() {
        this.marked = true;
    }

    /**
     * Marks a task as undone.
     */
    public void unmark() {
        this.marked = false;
    }

    /*
    public String getStatusIcon() {
        return marked ? "X" : " ";
    }*/

    /**
     * Returns a string of the task as stored in the file.
     */
    public String toFileFormat() {
        String statusIcon = marked ? "1" : "0";
        return "T | " + statusIcon + " | " + taskName;
    }

    /**
     * Returns a string of the task as it should be displayed in the list.
     */
    @Override
    public String toString() {
        return "[T]" + "[" + (marked ? "X" : " ") + "] " + taskName;
    }
}
