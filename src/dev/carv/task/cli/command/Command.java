package dev.carv.task.cli.command;

public sealed interface Command permits AddCommand, VersionCommand, HelpCommand {

    void execute();

}
