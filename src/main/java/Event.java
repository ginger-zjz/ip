public class Event extends Task {
    private String from;
    private String to;

    public Event(String name) {
        super(name);
        this.from = "";
        this.to = "";
    }

    public void from(String s) {
        this.from = s;
    }

    public void to(String s) {
        this.to = s;
    }

    @Override
    public String toString() {
        return "[E]" + "[" + (marked ? "X" : " ") + "] " + taskName + " (from: " + from + " to: " + to + ")";
    }
}
