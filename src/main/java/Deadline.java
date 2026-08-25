public class Deadline extends Task {
    private String by;

    public Deadline(String name) {
        super(name);
        this.by = "";
    }

    public void by(String s) {
        this.by = s;
    }

    @Override
    public String toFileFormat() {
        String statusIcon = marked ? "1" : "0";
        return "D | " + statusIcon + " | " + taskName + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + "[" + (marked ? "X" : " ") + "] " + taskName + " (by: " + by + ")";
    }
}
