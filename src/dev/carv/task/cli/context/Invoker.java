package dev.carv.task.cli.context;

import dev.carv.task.cli.command.*;
import dev.carv.task.cli.parser.ArgumentsParser;
import dev.carv.task.cli.service.TaskService;

public class Invoker {

    private final Command command;

    public Invoker(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("No arguments provided");
        }
        var parsed = new ArgumentsParser(args).parse();
        var option = parsed.getKey();
        var params = parsed.getValue();
        var service = new TaskService();

        command = switch (option) {
            case ADD                -> new AddCommand(service, params);
            case UPDATE             -> new UpdateCommand(service, params);
            case DELETE             -> new DeleteCommand(service, params);
            case MARK_IN_PROGRESS   -> new MarkInProgressCommand(service, params);
            case MARK_DONE          -> new MarkDoneCommand(service, params);
            case LIST               -> new ListCommand(service, params);
            case VERSION            -> new VersionCommand();
            case HELP               -> new HelpCommand();
        };
    }

    public void invoke() {
        command.execute();
    }

}
