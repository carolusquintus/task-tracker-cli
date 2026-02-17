package dev.carv.task.cli.command;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.repository.Repository;

import java.util.List;

public final class AddCommand implements Command {

    private final String description;
    private final Repository<Task, Integer> repository;

    public AddCommand(Repository<Task, Integer> repository, List<String> params) {
        this.repository = repository;
        if (params.isEmpty()) {
            throw new IllegalArgumentException("Description is required for add command");
        }
        this.description = params.getFirst();
    }

    @Override
    public void execute() {
        var task = new Task(description);
        int id = repository.insert(task);
        IO.println("Task added successfully (): " + id);
    }

}
