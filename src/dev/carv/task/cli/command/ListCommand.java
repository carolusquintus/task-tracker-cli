package dev.carv.task.cli.command;

import dev.carv.task.cli.domain.Status;
import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.service.TaskService;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

public final class ListCommand implements Command {

    private Status query;
    private final TaskService service;
    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ListCommand(TaskService service, List<String> params) {
        this.service = service;
        if (!params.isEmpty()) {
            this.query = Status.fromValue(params.getFirst());
        }
    }

    @Override
    public void execute() {
        var tasks = service.listTasks(query);
        printTable(tasks);
    }

    private void printTable(List<Task> tasks) {
        var columnSizes = collectColumnSizes(tasks);

        printLine(columnSizes);
        printHeader(columnSizes);
        printLine(columnSizes);
        printRows(tasks, columnSizes);
        printLine(columnSizes);
    }

    private Map<String, Header> collectColumnSizes(List<Task> tasks) {
        var result = new LinkedHashMap<String, Header>();
        result.put("id",           new Header("ID"));
        result.put("description",  new Header("DESCRIPTION"));
        result.put("status",       new Header("STATUS"));
        result.put("createdAt",    new Header("CREATED AT"));
        result.put("updatedAt",    new Header("UPDATED AT"));

        tasks.forEach(t -> {
            result.computeIfPresent("id",           (k, h) -> h.withSize(max(h.size(), t.idString().length())));
            result.computeIfPresent("description",  (k, h) -> h.withSize(max(h.size(), t.shortDescription().length())));
            result.computeIfPresent("status",       (k, h) -> h.withSize(max(h.size(), t.status().name().length())));
            result.computeIfPresent("createdAt",    (k, h) -> h.withSize(max(h.size(), t.createdAtFormatted(FORMAT_DATE).length())));
            result.computeIfPresent("updatedAt",    (k, h) -> h.withSize(max(h.size(), t.updatedAtFormatted(FORMAT_DATE).length())));
        });

        result.replaceAll((k, h) -> h.withSize(h.size() + 2));

        return result;
    }

    private void printLine(Map<String, Header> columns) {
        var builder = new StringBuilder();
        columns.entrySet().forEach(e ->
            builder.append('+').append("-".repeat(e.getValue().size()))
        );
        builder.append('+');
        IO.println(builder.toString());
    }

    private void printHeader(Map<String, Header> columns) {
        var builder = new StringBuilder();
        columns.entrySet().forEach(e -> {
            var h = e.getValue();
            builder.append('|').append(' ').append(h.title()).append(" ".repeat(h.size() - (h.title().length() + 1)));
        });
        builder.append('|');
        IO.println(builder.toString());
    }

    private void printRows(List<Task> tasks, Map<String, Header> columns) {
        tasks.forEach(t -> IO.println(
            new StringBuilder()
                .append(cell(t.id(), columns.get("id")))
                .append(cell(t.shortDescription(), columns.get("description")))
                .append(cell(t.status().name(), columns.get("status")))
                .append(cell(t.createdAtFormatted(FORMAT_DATE), columns.get("createdAt")))
                .append(cell(t.updatedAtFormatted(FORMAT_DATE), columns.get("updatedAt")))
                .append('|')
                .toString()
        ));
    }

    private String cell(Object cell, Header header) {
        var builder = new StringBuilder().append('|');
        return switch (cell) {
            case Number n ->    builder.append(" ".repeat(header.size() - n.toString().length() - 1)).append(n).append(' ').toString();
            case String s ->    builder.append(' ').append(s).append(" ".repeat(header.size() - s.length() - 2)).append(' ').toString();
            default ->          builder.append(" ".repeat(header.size())).toString();
        };
    }

    record Header(String title, Integer size) {

        Header(String title) {
            this(title, title.length());
        }

        public Header withSize(Integer size) {
            return new Header(this.title, size);
        }

    }

}
