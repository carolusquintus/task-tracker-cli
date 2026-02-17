package dev.carv.task.cli.domain;

import java.time.LocalDateTime;

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
    };

    public String toString() {
        return preFormatted().formatted(id, description, status, createdAt, updatedAt);
    }

}
