package dev.carv.task.cli.command;

public sealed interface Command permits AddCommand, UpdateCommand, ListCommand, VersionCommand, HelpCommand {

    void execute();

}
