package dev.carv.task.cli.command;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.service.TaskService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

public final class ShowCommand implements Command {

    private final Long id;
    private final TaskService service;

    public ShowCommand(TaskService service, List<String> params) {
        this.service = service;
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
        var task = service.show(new Task(id));


    }

    private Map<String, Row> collectRowSizes(Task task) {
        var result = new LinkedHashMap<String, Row>();
        result.put("id",           new Row("ID"));
        result.put("description",  new Row("DESCRIPTION"));
        result.put("status",       new Row("STATUS"));
        result.put("createdAt",    new Row("CREATED AT"));
        result.put("updatedAt",    new Row("UPDATED AT"));

        var x = task.description().lines().mapToInt(String::length).max().orElse(0);
        var y = (int) task.description().lines().count();

        result.computeIfPresent("id",           (k, h) -> h.withSizes(x, 1));
        result.computeIfPresent("description",  (k, h) -> h.withSizes(x, y));
        result.computeIfPresent("status",       (k, h) -> h.withSizes(max(h.x(), task.status().name().length()), h.y()));
        result.computeIfPresent("createdAt",    (k, h) -> h.withSizes(max(h.x(), task.createdAtFormatted(FORMAT_DATE).length()), h.y()));
        result.computeIfPresent("updatedAt",    (k, h) -> h.withSizes(max(h.x(), task.updatedAtFormatted(FORMAT_DATE).length()), h.y()));

        result.replaceAll((k, h) -> h.withSizes(h.x() + 2, h.y() + 1));
        return result;
    }

    record Row(String header, Integer x, Integer y) {

        Row(String header) {
            this(header, 1, 1);
        }

        public Row withSizes(Integer x, Integer y) {
            return new Row(header, x, y);
        }

    }
}
