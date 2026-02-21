package dev.carv.task.cli.service;

import dev.carv.task.cli.domain.Status;
import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.mapper.TaskMapper;
import dev.carv.task.cli.repository.Repository;
import dev.carv.task.cli.repository.TaskRepository;

import java.util.List;
import java.util.Map;

public final class TaskService {

    private final TaskMapper mapper;
    private final Repository<Map<String, Object>, Long> repository;

    public TaskService() {
        this.mapper = new TaskMapper();
        this.repository = new TaskRepository();
    }

    public Long add(Task task) {
        var taskMap = mapper.toMap(task);
        var taskSaved = repository.save(taskMap);
        return mapper.toTask(taskSaved).id();
    }

    public void update(Task task) {
        var found = repository.findById(task.id());
        found.put("description", task.description());
        found.put("updatedAt", task.updatedAt().toString());

        var foundTask = mapper.toTask(found);

        switch (foundTask.status()) {
            case TODO, IN_PROGRESS -> repository.update(found);
            case DONE -> throw new IllegalStateException("Task is already done and cannot be updated further");
        }
    }

    public void delete(Task task) {
        var found = repository.findById(task.id());
        repository.delete(found);
    }

    public List<Task> listTasks(Status query) {
        var task = switch (query) {
            case null -> repository.findAll();
            case TODO, IN_PROGRESS, DONE -> repository.findAllByStatus(query.name());
        };
        return task.stream()
            .map(mapper::toTask)
            .toList();
    }

}
