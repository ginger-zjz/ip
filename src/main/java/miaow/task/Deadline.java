package miaow.task;

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


    /**
     * Sets the deadline as a date
     * @param date String input for date
     */
    public void by(String date) {
        this.by = date;
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

    /**
     * Returns a string of date formatted as required.
     */
    public String getFormattedDate() {
        if (byDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            return byDate.format(formatter);
        }
        return by;
    }

    /**
     * Returns a string of the task as stored in the file.
     */
    @Override
    public String toFileFormat() {
        String statusIcon = marked ? "1" : "0";
        return "D | " + statusIcon + " | " + taskName + " | " + by;
    }

    /**
     * Returns a string of the task as it should be displayed in the list.
     */
    @Override
    public String toString() {
        return "[D]" + "[" + (marked ? "X" : " ") + "] " + taskName + " (by: " + getFormattedDate() + ")";
    }
}
