package dev.carv.task.cli.command;

public sealed interface Command permits
    AddCommand, UpdateCommand, DeleteCommand,
    MarkInProgressCommand, MarkDoneCommand,
    ListCommand, VersionCommand, HelpCommand {

    void execute();

}
