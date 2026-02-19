package dev.carv.task.cli.command;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.repository.Repository;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ListCommand implements Command {

    private String query;
    private final Repository<Task, Integer> repository;
    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ListCommand(Repository<Task, Integer> repository, List<String> params) {
        this.repository = repository;
        if (!params.isEmpty()) {
            this.query = params.getFirst();
        }
    }

    @Override
    public void execute() {
        var tasks = repository.getAll();

        printTable(tasks);
    }

    private Map<String, Integer> collectHighestValuesPerAttribute(List<Task> tasks) {
        var result = new LinkedHashMap<>(Map.of(
            "id", 2,
            "description", 0,
            "status", 0,
            "createdAt", 0,
            "updatedAt", 0
        ));

        for (var task : tasks) {
            result.put("id", Math.max(result.get("id"), task.idString().length()));
            result.put("description", Math.max(result.get("description"), task.description().length()));
            result.put("status", Math.max(result.get("status"), task.status().name().length()));
            result.put("createdAt", Math.max(result.get("createdAt"), task.createdAtFormatted(FORMAT_DATE).length()));
            result.put("updatedAt", Math.max(result.get("updatedAt"), task.updatedAtFormatted(FORMAT_DATE).length()));
        }

        result.replaceAll((k, v) -> v + 2);

        return result;
    }

    private void printTable(List<Task> tasks) {
        var collected = collectHighestValuesPerAttribute(tasks);

        var line =  new StringBuilder()
            .append("+").append("-".repeat(collected.get("id")))
            .append("+").append("-".repeat(collected.get("description")))
            .append("+").append("-".repeat(collected.get("status")))
            .append("+").append("-".repeat(collected.get("createdAt")))
            .append("+").append("-".repeat(collected.get("updatedAt"))).append('+');

        var header = new StringBuilder()
            .append("|").append(" ID").append(" ".repeat(collected.get("id") - 3))
            .append("|").append(" DESCRIPTION").append(" ".repeat(collected.get("description") - 12))
            .append("|").append(" STATUS").append(" ".repeat(collected.get("status") - 7))
            .append("|").append(" CREATED AT").append(" ".repeat(collected.get("createdAt") - 11))
            .append("|").append(" UPDATED AT").append(" ".repeat(collected.get("updatedAt") - 11)).append('|');

        IO.println(line.toString());
        IO.println(header.toString());
        IO.println(line.toString());
        for (var task : tasks) {
            var row = new StringBuilder()
                .append("|").append(" ".repeat(collected.get("id") - task.idString().length() - 1)).append(task.id()).append(" ")
                .append("|").append(" ").append(task.description()).append(" ".repeat(collected.get("description") - task.description().length() - 2)).append(" ")
                .append("|").append(" ").append(task.status().name()).append(" ".repeat(collected.get("status") - task.status().name().length() - 2)).append(" ")
                .append("|").append(" ").append(task.createdAt().toString()).append(" ".repeat(collected.get("createdAt") - task.createdAt().toString().length() - 2)).append(" ")
                .append("|").append(" ").append(task.updatedAt().toString()).append(" ".repeat(collected.get("updatedAt") - task.updatedAt().toString().length() - 2)).append(" ").append("|");
            IO.println(row.toString());
        }
        IO.println(line.toString());
    }

}
