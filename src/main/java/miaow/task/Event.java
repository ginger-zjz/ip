package miaow.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * event
 */
public class Event extends Task {
    private String from;
    private String to;
    private LocalDate fromDate;
    private LocalDate toDate;

    /**
     * event constructor
     * @param name
     */
    public Event(String name) {
        super(name);
        //this.from = "";
        //this.to = "";
    }

    /**
     * Sets the start date as a date
     * @param date String input for date
     */
    public void from(String date) {
        this.from = date;
        try {
            // Try yyyy-MM-dd format first
            this.fromDate = LocalDate.parse(date);
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
     * @param date String input for date
     */
    public void to(String date) {
        this.to = date;
        try {
            // Try yyyy-MM-dd format first
            this.toDate = LocalDate.parse(date);
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
        String statusIcon = super.isDone() ? "1" : "0";
        return "E | " + statusIcon + " | " + super.getDescription() + " | " + from + " | " + to;
    }

    /**
     * Returns a string of the task as it should be displayed in the list.
     */
    @Override
    public String toString() {
        return "[E]" + "[" + (super.isDone() ? "X" : " ") + "] " + super.getDescription() + " (from: "
                + getFormattedFrom() + " to: " + getFormattedTo() + ")";
    }
}
