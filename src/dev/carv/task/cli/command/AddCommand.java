package dev.carv.task.cli.command;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.service.TaskService;

import java.util.List;

public final class AddCommand implements Command {

    private final String description;
    private final TaskService service;

    public AddCommand(TaskService service, List<String> params) {
        this.service = service;
        if (params.isEmpty()) {
            throw new IllegalArgumentException("Description is required to add a new task");
        }
        this.description = params.getFirst().replaceAll("\\n", "");
    }

    @Override
    public void execute() {
        Long id = service.add(new Task(description));
        IO.println("Task added successfully (ID: %d)".formatted(id));
    }

}
