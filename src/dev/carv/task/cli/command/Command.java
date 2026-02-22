package dev.carv.task.cli.command;

public sealed interface Command permits
    AddCommand, UpdateCommand, DeleteCommand,
    MarkInProgressCommand,
    ListCommand, VersionCommand, HelpCommand {

    void execute();

}
