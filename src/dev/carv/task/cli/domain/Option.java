package dev.carv.task.cli.domain;

import java.util.Arrays;
import java.util.List;

public enum Option {

    ADD("-a", "add"),
    UPDATE("-u", "update"),
    DELETE("-d", "delete"),
    MARK_IN_PROGRESS("-mip", "mark-in-progress"),
    MARK_DONE("-md", "mark-done"),
    LIST("-l", "list"),
    VERSION("-v", "version"),
    HELP( "-h", "help");

    private final List<String> alternativeNames;

    Option(String... altNames) {
        this.alternativeNames = Arrays.stream(altNames).toList();
    }

    public static Option fromString(String option) {
        return Arrays.stream(values())
            .filter(o -> o.alternativeNames.contains(option))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unrecognized option: %s".formatted(option)));
    }

}
