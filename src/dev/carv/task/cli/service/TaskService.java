package dev.carv.task.cli.service;

import dev.carv.task.cli.domain.Task;
import dev.carv.task.cli.mapper.TaskMapper;
import dev.carv.task.cli.repository.Repository;
import dev.carv.task.cli.repository.TaskRepository;

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

}
