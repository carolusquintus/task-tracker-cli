package dev.carv.task.cli.command;

public final class HelpCommand implements Command {

    @Override
    public void execute() {
        IO.println("""
            Usage: task [OPTION] [params]...
              or   task [OPTION]
            
            where option is one of:
              -a, add <description>
                            Add a new task with default status: todo
              -u, update <id> <description>
                            Update the description of a task by given id
              -mip, mark-in-progress <id>
                            Mark a task as in-progress by given id
              -md, mark-done <id>
                            Mark a task as done by given id
              -d, delete <id>
                            Delete a task by given id
              -l, list <status> | none
                            List task by given status or list all tasks
              -h, help
                            Print this help message
              -v, version
                            Print version information
            """);
    }

}
