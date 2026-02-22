package dev.carv.task.cli.command;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.service.TaskService;

import java.util.List;

import static dev.carv.task.cli.domain.Status.DONE;

public final class MarkDoneCommand implements Command {

    private final Long id;
    private final TaskService service;

    public MarkDoneCommand(TaskService service, List<String> params) {
        this.service = service;
        if (params.isEmpty()) {
            throw new IllegalArgumentException("ID is required to mark a task as done");
        }
        try {
            this.id = Long.parseLong(params.getFirst());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID must be a valid number");
        }
    }

    @Override
    public void execute() {
        service.update(new Task(id, DONE));
        IO.println("Task marked as done successfully (ID: %d)".formatted(id));
    }

}
