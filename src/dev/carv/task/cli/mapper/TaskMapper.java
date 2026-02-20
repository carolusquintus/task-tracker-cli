package dev.carv.task.cli.mapper;

import dev.carv.task.cli.domain.Status;
import dev.carv.task.cli.domain.Task;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class TaskMapper {

    public Task toTask(Map<String, Object> map) {
        return new Task(
            (Long) map.get("id"),
            (String) map.get("description"),
            Status.valueOf((String) map.get("status")),
            (LocalDateTime) map.get("createdAt"),
            (LocalDateTime) map.get("updatedAt")
        );
    }

    public Map<String, Object> toMap(Task task) {
        var map = new LinkedHashMap<String, Object>();
        map.put("id", task.id());
        map.put("description", task.description());
        map.put("status", task.status().name());
        map.put("createdAt", task.createdAt());
        map.put("updatedAt", task.updatedAt());
        return map;
    }

}
