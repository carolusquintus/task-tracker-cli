package dev.carv.task.cli.command;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.service.TaskService;

import java.util.List;

public final class DeleteCommand implements Command {

    private final Long id;
    private final TaskService service;

    public DeleteCommand(TaskService service, List<String> params) {
        this.service = service;
        if (params.isEmpty()) {
            throw new IllegalArgumentException("ID is required to delete a task");
        }
        try {
            this.id = Long.parseLong(params.getFirst());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID must be a valid number");
        }
    }

    @Override
    public void execute() {
        service.delete(new Task(id));
        IO.println("Task deleted successfully (ID: %d)".formatted(id));
    }

}
