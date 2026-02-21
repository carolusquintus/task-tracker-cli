package dev.carv.task.cli.mapper;

import dev.carv.task.cli.domain.Status;
import dev.carv.task.cli.domain.Task;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class TaskMapper {

    public Task toTask(Map<String, Object> map) {
        return new Task(
            (Long) map.get("id"),
            (String) map.get("description"),
            Status.valueOf((String) map.get("status")),
            ofNullable(map.get("createdAt")).map(String.class::cast).map(LocalDateTime::parse).orElse(null),
            ofNullable(map.get("updatedAt")).map(String.class::cast).map(LocalDateTime::parse).orElse(null)
        );
    }

    public Map<String, Object> toMap(Task task) {
        var map = new LinkedHashMap<String, Object>();
        map.put("id", task.id());
        map.put("description", task.description());
        map.put("status", ofNullable(task.status()).map(Status::name).orElse(null));
        map.put("createdAt", ofNullable(task.createdAt()).map(LocalDateTime::toString).orElse(null));
        map.put("updatedAt", ofNullable(task.updatedAt()).map(LocalDateTime::toString).orElse(null));
        return map;
    }

}
