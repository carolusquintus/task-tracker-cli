package dev.carv.task.cli.command;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.service.TaskService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        var task = service.getTask(new Task(id));
        printTable(task);
    }

    private void printTable(Task task) {


    }

    private Row collectRowSizes(Task task) {
        var headers = List.of("ID", "DESCRIPTION", "STATUS", "CREATED AT", "UPDATED AT");

        var longestHeader = headers.stream()
            .max(Comparator.comparingInt(String::length))
            .orElse("");

        var longestLine = task.description().lines()
            .max(Comparator.comparingInt(String::length))
            .orElse("");

        return new Row(longestHeader.length() + 2, longestLine.length() + 2);
    }

    record Row(Integer headerWidth, Integer dataWidth) {}
}
