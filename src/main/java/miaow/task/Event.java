package miaow.task;

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

    /**
     * Sets the start date as a date
     * @param s String input for date
     */
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

    /**
     * Sets the due date as a date
     * @param s String input for date
     */
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

    /**
     * converts a LocalDate into date
     * @param dateTime LocalDate input for conversion
     */
    public String getFormattedDate(LocalDate dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            return dateTime.format(formatter);
        }
        return "Invalid date";
    }

    /**
     * Returns the start date
     */
    public String getFormattedFrom() {
        return getFormattedDate(fromDate);
    }

    /**
     * Returns the end date
     */
    public String getFormattedTo() {
        return getFormattedDate(toDate);
    }

    /**
     * Returns a string of the task as stored in the file.
     */
    @Override
    public String toFileFormat() {
        String statusIcon = marked ? "1" : "0";
        return "E | " + statusIcon + " | " + taskName + " | " + from + " | " + to;
    }

    /**
     * Returns a string of the task as it should be displayed in the list.
     */
    @Override
    public String toString() {
        return "[E]" + "[" + (marked ? "X" : " ") + "] " + taskName + " (from: " + getFormattedFrom() +
                " to: " + getFormattedTo() + ")";
    }
}
