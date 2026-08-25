import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private String from;
    private String to;
    private LocalDate fromDate;
    private LocalDate toDate;

    public Event(String name) {
        super(name);
        //this.from = "";
        //this.to = "";
    }

    public void from(String s) {
        this.from = s;
        try {
            // Try yyyy-MM-dd format first
            this.fromDate = LocalDate.parse(s);
        } catch (DateTimeParseException e1) {
            try {
                // Try dd/MM/yyyy format (e.g., 2/12/2019)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                this.fromDate = LocalDate.parse(from, formatter);
            } catch (DateTimeParseException e2) {
                // If parsing fails, keep as string
                this.fromDate = null;
            }
        }
    }

    public void to(String s) {
        this.to = s;
        try {
            // Try yyyy-MM-dd format first
            this.toDate = LocalDate.parse(s);
        } catch (DateTimeParseException e1) {
            try {
                // Try dd/MM/yyyy format (e.g., 2/12/2019)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                this.toDate = LocalDate.parse(to, formatter);
            } catch (DateTimeParseException e2) {
                // If parsing fails, keep as string
                this.toDate = null;
            }
        }
    }

    public String getFormattedDate(LocalDate dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            return dateTime.format(formatter);
        }
        return "Invalid date";
    }

    public String getFormattedFrom() {
        return getFormattedDate(fromDate);
    }

    public String getFormattedTo() {
        return getFormattedDate(toDate);
    }

    @Override
    public String toFileFormat() {
        String statusIcon = marked ? "1" : "0";
        return "E | " + statusIcon + " | " + taskName + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + "[" + (marked ? "X" : " ") + "] " + taskName + " (from: " + getFormattedFrom() +
                " to: " + getFormattedTo() + ")";
    }
}
