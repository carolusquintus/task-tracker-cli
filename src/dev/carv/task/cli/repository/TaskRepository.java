package dev.carv.task.cli.repository;

import dev.carv.task.cli.json.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskRepository implements Repository<Map<String, Object>, Long> {

    private List<Map<String, Object>> tasks;
    private JsonParser parser = JsonParser.getInstance();
    private static final Path TASKS_JSON = Path.of("./tasks-stored.json");

    public TaskRepository() {
        try {
            if (Files.exists(TASKS_JSON)) {
                tasks = parseTask();
            } else {
                var perms = PosixFilePermissions.fromString("rw-rw-rw-");
                var attrs = PosixFilePermissions.asFileAttribute(perms);
                Files.createFile(TASKS_JSON, attrs);
                Files.writeString(TASKS_JSON, "[]");
                tasks = new ArrayList<>();
            }
        } catch (IOException e) {
            System.err.println("Error reading tasks file: " + e.getMessage());
        }
    }

    private List<Map<String, Object>> parseTask() {
        var jsonString = parser.readJson(TASKS_JSON);
        var jsonParsed = parser.parseJson(jsonString);
        if (jsonParsed instanceof List<?> l) {
            return new ArrayList<>(l.stream()
                .map(o -> (Map<String, Object>) o)
                .toList());
        }
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> save(Map<String, Object> task) {
        if (tasks.isEmpty()) {
            task.put("id", 1L);
        } else {
            var lastId = (Long) tasks.getLast().get("id");
            task.put("id", lastId + 1L);
        }
        tasks.add(task);
        storeToJson();
        return task;
    }

    @Override
    public List<Map<String, Object>> findAll() {
        return List.of();
    }

//    @Override
//    public List<Task> findAll() {
//        return List.of(
//            new Task(1, "Some description", TODO, LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
//            new Task(2, "Some description even more longer", IN_PROGRESS, LocalDateTime.now(), LocalDateTime.now().plusDays(2)),
//            new Task(3, "I have work pending", DONE, LocalDateTime.now(), LocalDateTime.now().plusDays(3))
//        );
//    }

    private void storeToJson() {
        try {
            Files.writeString(TASKS_JSON, parser.printJson(tasks));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
