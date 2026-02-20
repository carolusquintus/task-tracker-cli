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

    private Map<String, Integer> collectColumnSizes(List<Task> tasks) {
        var result = new LinkedHashMap<>(Map.of(
            "id",           "ID".length(),
            "description",  "DESCRIPTION".length(),
            "status",       "STATUS".length(),
            "createdAt",    "CREATED AT".length(),
            "updatedAt",    "UPDATED AT".length()
        ));

        for (var task : tasks) {
            result.put("id", max(result.get("id"), task.idString().length()));
            result.put("description", max(result.get("description"), task.description().length()));
            result.put("status", max(result.get("status"), task.status().name().length()));
            result.put("createdAt", max(result.get("createdAt"), task.createdAtFormatted(FORMAT_DATE).length()));
            result.put("updatedAt", max(result.get("updatedAt"), task.updatedAtFormatted(FORMAT_DATE).length()));
        }

        result.replaceAll((k, v) -> v + 2);

        return result;
    }

    private static void printLine(Map<String, Integer> columnSizes) {
        IO.println(new StringBuilder()
            .append("+").append("-".repeat(columnSizes.get("id")))
            .append("+").append("-".repeat(columnSizes.get("description")))
            .append("+").append("-".repeat(columnSizes.get("status")))
            .append("+").append("-".repeat(columnSizes.get("createdAt")))
            .append("+").append("-".repeat(columnSizes.get("updatedAt"))).append('+')
            .toString());
    }

    private static void printHeader(Map<String, Integer> columnSizes) {
        IO.println(new StringBuilder()
            .append("|").append(" ID").append(" ".repeat(columnSizes.get("id") - 3))
            .append("|").append(" DESCRIPTION").append(" ".repeat(columnSizes.get("description") - 12))
            .append("|").append(" STATUS").append(" ".repeat(columnSizes.get("status") - 7))
            .append("|").append(" CREATED AT").append(" ".repeat(columnSizes.get("createdAt") - 11))
            .append("|").append(" UPDATED AT").append(" ".repeat(columnSizes.get("updatedAt") - 11)).append('|')
            .toString());
    }

    private static void printRows(List<Task> tasks, Map<String, Integer> columnSizes) {
        tasks.forEach(task -> IO.println(
            new StringBuilder()
                .append("|").append(" ".repeat(columnSizes.get("id") - task.idString().length() - 1)).append(task.id()).append(" ")
                .append("|").append(" ").append(task.description()).append(" ".repeat(columnSizes.get("description") - task.description().length() - 2)).append(" ")
                .append("|").append(" ").append(task.status().name()).append(" ".repeat(columnSizes.get("status") - task.status().name().length() - 2)).append(" ")
                .append("|").append(" ").append(task.createdAtFormatted(FORMAT_DATE)).append(" ".repeat(columnSizes.get("createdAt") - task.createdAtFormatted(FORMAT_DATE).length() - 2)).append(" ")
                .append("|").append(" ").append(task.updatedAtFormatted(FORMAT_DATE)).append(" ".repeat(columnSizes.get("updatedAt") - task.updatedAtFormatted(FORMAT_DATE).length() - 2)).append(" ").append("|").toString()
        ));
    }

}
