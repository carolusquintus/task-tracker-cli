package dev.carv.task.cli.domain;

import java.util.Arrays;
import java.util.List;

public enum Option {

    ADD("add", "--add", "-a"),
    UPDATE("update", "--update", "-u"),
    DELETE("delete", "--delete", "-d"),
    MARK_IN_PROGRESS("mark-in-progress", "--mark-in-progress", "-mip"),
    MARK_DONE("mark-done", "--mark-done", "-md"),
    LIST("list", "--list", "-l"),
    VERSION("version", "--version", "-v"),
    HELP( "help", "--help", "-h");

    private static String raw;
    private final List<String> alternativeNames;

    Option(String... altNames) {
        this.alternativeNames = Arrays.stream(altNames).toList();
    }

    public static Option fromString(String option) {
        return Arrays.stream(values())
            .filter(o -> o.alternativeNames.contains(option))
            .peek(o -> raw = option)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unrecognized option: " + option));
    }

    public String getRaw() {
        return raw;
    }

}
