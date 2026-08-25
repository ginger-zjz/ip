import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private LocalDate byDate;
    private String by;

    public Deadline(String name) {
        super(name);
        //this.by = "";
    }

    public void by(String s) {
        this.by = s;
        try {
            // Try yyyy-MM-dd format first
            this.byDate = LocalDate.parse(by);
        } catch (DateTimeParseException e1) {
            try {
                // Try dd/MM/yyyy format (e.g., 2/12/2019)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                this.byDate = LocalDate.parse(by, formatter);
            } catch (DateTimeParseException e2) {
                // If parsing fails, keep as string
                this.byDate = null;
            }
        }
    }

    public LocalDate getByDate() {
        return byDate;
    }

    public String getFormattedDate() {
        if (byDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            return byDate.format(formatter);
        }
        return by;
    }

    @Override
    public String toFileFormat() {
        String statusIcon = marked ? "1" : "0";
        return "D | " + statusIcon + " | " + taskName + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + "[" + (marked ? "X" : " ") + "] " + taskName + " (by: " + getFormattedDate() + ")";
    }
}
