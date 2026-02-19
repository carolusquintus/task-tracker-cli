package dev.carv.task.cli.repository;

import dev.carv.task.cli.domain.Status;
import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.json.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static dev.carv.task.cli.domain.Status.*;

public class TaskRepository implements Repository<Task, Integer> {

    private static final Path TASKS_JSON = Path.of("./tasks-stored.json");
    private JsonParser parser = JsonParser.getInstance();

    private List<Task> tasks;

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

    @Override
    public List<Task> getAll() {
        return List.of(
            new Task(1, "Some description", TODO, LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
            new Task(2, "Some description even more longer", IN_PROGRESS, LocalDateTime.now(), LocalDateTime.now().plusDays(2)),
            new Task(3, "I have work pending", DONE, LocalDateTime.now(), LocalDateTime.now().plusDays(3))
        );
    }

}
