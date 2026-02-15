package dev.carv.task.cli.context;

import dev.carv.task.cli.command.Command;
import dev.carv.task.cli.command.HelpCommand;
import dev.carv.task.cli.command.VersionCommand;

public class Invoker {

    private ArgumentsParser argumentParser;
    private Command command;

    public Invoker(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("No arguments provided");
        }
        this.argumentParser = new ArgumentsParser(args);
        var parsed = argumentParser.parse();
        var option = parsed.getKey();
        var params = parsed.getValue();

        var notImplemented = new IllegalArgumentException("Option: " + option + " is not implemented yet");

        command = switch (option) {
            case ADD                -> throw notImplemented;
            case UPDATE             -> throw notImplemented;
            case DELETE             -> throw notImplemented;
            case MARK_IN_PROGRESS   -> throw notImplemented;
            case MARK_DONE          -> throw notImplemented;
            case LIST               -> throw notImplemented;
            case VERSION            -> new VersionCommand();
            case HELP               -> new HelpCommand();
        };
    }

    public void invoke() {
        command.execute();
    }

}
