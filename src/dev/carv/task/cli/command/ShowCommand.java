package dev.carv.task.cli.command;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.service.TaskService;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.max;

public final class ShowCommand implements Command {

    private final Long id;
    private final TaskService service;
    private final DateTimeFormatter formatter;

    public ShowCommand(TaskService service, List<String> params, DateTimeFormatter formatter) {
        this.service = service;
        this.formatter = formatter;
        if (params.isEmpty()) {
            throw new IllegalArgumentException("ID is required to show the detail of a task");
        }
        try {
            this.id = Long.parseLong(params.getFirst());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID must be a valid number");
        }
    }

    @Override
    public void execute() {
        var task = service.find(new Task(id));
        printTable(task);
    }

    private Row collectRowSizes(Task task) {
        var longestHeader = Stream.of("ID", "DESCRIPTION", "STATUS", "CREATED AT", "UPDATED AT")
            .max(Comparator.comparingInt(String::length))
            .orElse("TITLE BIG ENOUGH");

        var longestLine = max(task.descriptionLongestLine(), task.createdAtFormatted(formatter).length());

        return new Row(longestHeader.length() + 2, longestLine + 2);
    }

    private void printTable(Task t) {
        var row = collectRowSizes(t);
        var first = true;

        printLine(row);
        printRow(row, "ID", t.id());
        printLine(row);
        for (var line : t.descriptionLines()) {
            printRow(row, first ? "DESCRIPTION" : "", line);
            first = false;
        }
        printLine(row);
        printRow(row, "STATUS", t.status().name());
        printLine(row);
        printRow(row, "CREATED AT", t.createdAtFormatted(formatter));
        printLine(row);
        printRow(row, "UPDATED AT", t.updatedAtFormatted(formatter));
        printLine(row);
    }

    private void printLine(Row r) {
        IO.println(new StringBuilder()
            .append('+').append("-".repeat(r.titleWidth()))
            .append('+').append("-".repeat(r.dataWidth()))
            .append('+').toString());
    }

    private void printRow(Row r, String title, Object data) {
        printTitle(r, title);
        printData(r, data);
    }

    private void printTitle(Row r, String title) {
        IO.print(new StringBuilder()
            .append('|').append(' ').append(title).append(" ".repeat(r.titleWidth() - title.length() - 1)).append('|'));
    }

    private void printData(Row r, Object data) {
        var builder = new StringBuilder();
        switch (data) {
            case Number n   -> builder.append(" ".repeat(r.dataWidth - n.toString().length() - 1)).append(n).append(' ');
            case String s   -> builder.append(' ').append(s).append(" ".repeat(r.dataWidth() - s.length() - 1));
            default         -> builder.append(" ".repeat(r.dataWidth()));
        }
        builder.append('|');
        IO.println(builder.toString());
    }

    record Row(Integer titleWidth, Integer dataWidth) {}

}
