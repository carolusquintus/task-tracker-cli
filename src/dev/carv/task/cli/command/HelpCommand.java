package dev.carv.task.cli.command;

public final class HelpCommand implements Command {

    @Override
    public void execute() {
        IO.println("""
            Usage: task [OPTION] [params]...
              or   task [OPTION]
            
            where option is one of:
              add, --add, a <description>
                            add a new task with default status: todo
              list, --list, -l <status>|none
                            list task by given status or list all tasks
              help, --help, -h
                            print this help message
              version, --version, -v
                            print version information
            """);
    }

}
