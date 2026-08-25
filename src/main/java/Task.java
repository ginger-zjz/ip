public class Task {
    String taskName;
    boolean marked;

    public Task(String name) {
        this.taskName = name;
        this.marked = false;
    }

    public void mark() {
        this.marked = true;
    }

    public void unmark() {
        this.marked = false;
    }

    public String toFileFormat() {
        String statusIcon = marked ? "1" : "0";
        return "T | " + statusIcon + " | " + taskName;
    }

    @Override
    public String toString() {
        return "[T]" + "[" + (marked ? "X" : " ") + "] " + taskName;
    }
}
