package dev.carv.task.cli.command;

public sealed interface Command permits AddCommand, ListCommand, VersionCommand, HelpCommand {

    void execute();

}
