package dev.carv.task.cli.command;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.service.TaskService;

import java.util.List;

public final class UpdateCommand implements Command {

    private final Long id;
    private final String description;
    private final TaskService service;

    public UpdateCommand(TaskService service, List<String> params) {
        this.service = service;
        if (params.size() < 2) {
            throw new IllegalArgumentException("ID and description are required to update a task");
        }
        try {
            this.id = Long.parseLong(params.getFirst());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID must be a valid number");
        }
        this.description = params.get(1).replaceAll("\\n", "");
    }

    @Override
    public void execute() {
        service.update(new Task(id, description));
        IO.println("Task updated successfully (ID: %d)".formatted(id));
    }

}
