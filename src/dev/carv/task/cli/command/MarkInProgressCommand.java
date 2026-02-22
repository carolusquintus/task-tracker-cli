package dev.carv.task.cli.command;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.service.TaskService;

import java.util.List;

import static dev.carv.task.cli.domain.Status.IN_PROGRESS;

public final class MarkInProgressCommand implements Command {

    private final Long id;
    private final TaskService service;

    public MarkInProgressCommand(TaskService service, List<String> params) {
        this.service = service;
        if (params.isEmpty()) {
            throw new IllegalArgumentException("ID is required to mark a task as in-progress");
        }
        try {
            this.id = Long.parseLong(params.getFirst());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID must be a valid number");
        }
    }

    @Override
    public void execute() {
        service.update(new Task(id, IN_PROGRESS));
        IO.println("Task marked as in-progress successfully (ID: %d)".formatted(id));
    }

}
