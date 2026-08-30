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
