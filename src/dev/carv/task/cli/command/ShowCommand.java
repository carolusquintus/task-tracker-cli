package dev.carv.task.cli.command;

import dev.carv.task.cli.service.TaskService;

import java.util.List;

public final class ShowCommand implements Command {

    private final TaskService service;

    public ShowCommand(TaskService service, List<String> params) {
        this.service = service;
    }

    @Override
    public void execute() {


    }
}
