package dev.carv.task.cli.mapper;

import dev.carv.task.cli.domain.Status;
import dev.carv.task.cli.domain.Task;

import java.time.LocalDateTime;
import java.util.Map;

public class TaskMapper {

    public Task toTask(Map<String, Object> map) {
        return new Task(
            (Integer) map.get("id"),
            (String) map.get("description"),
            Status.valueOf((String) map.get("status")),
            (LocalDateTime) map.get("createdAt"),
            (LocalDateTime) map.get("updatedAt")
        );
    }

    public Map<String, Object> toMap(Task task) {
        return Map.of(
            "id",           task.id(),
            "description",  task.description(),
            "status",       task.status().name(),
            "createdAt",    task.createdAt(),
            "updatedAt",    task.updatedAt()
        );
    }

}
