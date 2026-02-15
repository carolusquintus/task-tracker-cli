package dev.carv.task.cli.command;

public final class VersionCommand implements Command {

    @Override
    public void execute() {
        IO.println("Task Tracker CLI version 1.0.0 by carolusquintus");
    }

}
