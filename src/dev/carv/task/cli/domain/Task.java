package dev.carv.task.cli.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static dev.carv.task.cli.domain.Status.TODO;
import static java.util.Objects.isNull;

public record Task(
    Long id,
    String description,
    Status status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public Task(Long id) {
        this(id, null, null, null, null);
    }

    public Task(String description) {
        this(null, description, TODO, LocalDateTime.now(), null);
    }

    public Task(Long id, String description) {
        this(id, description, null, null, LocalDateTime.now());
    }

    public String idString() {
        return Long.toString(id);
    }

    public String createdAtFormatted(DateTimeFormatter format) {
        return isNull(createdAt) ? "null" : format.format(createdAt);
    }

    public String updatedAtFormatted(DateTimeFormatter format) {
        return isNull(updatedAt) ? "null" : format.format(updatedAt);
    }

    public String toString() {
        return """
            {
                "id": %d,
                "description": "%s",
                "status": "%s",
                "createdAt": "%s",
                "updatedAt": "%s"
            }
            """.formatted(id, description, status, createdAt, updatedAt);
    }

}
