package dev.carv.task.cli.domain;

import java.util.Arrays;

public enum Status {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public static Status fromValue(String value) {
        return Arrays.stream(values())
            .filter(s -> s.value.equalsIgnoreCase(value) || s.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid status: " + value));
    }

}
