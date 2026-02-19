package dev.carv.task.cli.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Task(
    Integer id,
    String description,
    Status status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public Task(String description) {
        this(null, description, Status.TODO, LocalDateTime.now(), null);
    }

    public String preFormatted() {
        return """
            {
                "id": %d,
                "description": "%s",
                "status": "%s",
                "createdAt": "%s",
                "updatedAt": "%s"
            }""";
    }

    public String idString() {
        return Integer.toString(id);
    }

    public String createdAtFormatted(DateTimeFormatter format) {
        return format.format(createdAt);
    }

    public String updatedAtFormatted(DateTimeFormatter format) {
        return format.format(updatedAt);
    }

    public String toString() {
        return preFormatted().formatted(id, description, status, createdAt, updatedAt);
    }

}
