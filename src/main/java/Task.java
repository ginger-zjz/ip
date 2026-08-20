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

    @Override
    public String toString() {
        return "[" + (marked ? "X" : " ") + "] " + taskName;
    }
}
