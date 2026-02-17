package dev.carv.task.cli.repository;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.json.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;

public class TaskRepository implements Repository<Task, Integer> {

    private static final Path TASKS_JSON = Path.of("./tasks-stored.json");
    private JsonParser parser = JsonParser.getInstance();

    public TaskRepository() {
        try {
            if (Files.exists(TASKS_JSON)) {
                IO.println(parser.readJson(TASKS_JSON));
            } else {
                var perms = PosixFilePermissions.fromString("rw-rw-rw-");
                var attrs = PosixFilePermissions.asFileAttribute(perms);
                Files.createFile(TASKS_JSON, attrs);
            }
        } catch (IOException e) {
            System.err.println("Error reading tasks file: " + e.getMessage());
        }
    }

    @Override
    public Integer insert(Task type) {
        return 0;
    }

}
