package dev.carv.task.cli.command;

public sealed interface Command permits
    AddCommand, UpdateCommand, DeleteCommand,
    MarkInProgressCommand, MarkDoneCommand, ShowCommand,
    ListCommand, VersionCommand, HelpCommand {

    void execute();

}
