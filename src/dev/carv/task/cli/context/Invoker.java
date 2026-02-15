package dev.carv.task.cli.context;

import dev.carv.task.cli.command.Command;
import dev.carv.task.cli.command.VersionCommand;

public class Invoker {

    private ArgumentParser argumentParser;
    private Command command;

    public Invoker(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("No arguments provided");
        }
        this.argumentParser = new ArgumentParser(args);
        var params = argumentParser.parse();

        command = switch (params.getKey()) {
            case VERSION -> new VersionCommand();
        };
    }

    public void invoke() {
        command.execute();
    }

}
