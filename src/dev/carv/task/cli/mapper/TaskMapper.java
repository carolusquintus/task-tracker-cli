package dev.carv.task.cli.mapper;

import dev.carv.task.cli.domain.Status;
import dev.carv.task.cli.domain.Task;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class TaskMapper {

    public Task toTask(Map<String, Object> map) {
        return new Task(
            (Long) map.get("id"),
            (String) map.get("description"),
            Status.valueOf((String) map.get("status")),
            isNull(map.get("createdAt")) ? null : LocalDateTime.parse((String) map.get("createdAt")),
            isNull(map.get("updatedAt")) ? null : LocalDateTime.parse((String) map.get("updatedAt"))
        );
    }

    public Map<String, Object> toMap(Task task) {
        var map = new LinkedHashMap<String, Object>();
        map.put("id", task.id());
        map.put("description", task.description());
        map.put("status", task.status().name());
        map.put("createdAt", isNull(task.createdAt()) ? null : task.createdAt().toString());
        map.put("updatedAt", isNull(task.updatedAt()) ? null : task.updatedAt().toString());
        return map;
    }

}
