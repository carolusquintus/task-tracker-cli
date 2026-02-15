package dev.carv.task.cli.domain;

import java.util.Arrays;
import java.util.List;

public enum Instruction {

    ADD("add", "--add", "-a"),
    UPDATE("update", "--update", "-u"),
    DELETE("delete", "--delete", "-d"),
    MARK_IN_PROGRESS("mark-in-progress", "--mark-in-progress", "-mip"),
    MARK_DONE("mark-done", "--mark-done", "-md"),
    LIST("list", "--list", "-l"),
    VERSION("version", "--version", "-v"),
    HELP( "help", "--help", "-h");

    private final List<String> alternativeNames;

    Instruction(String... altNames) {
        this.alternativeNames = Arrays.stream(altNames).toList();
    }

    public static Instruction fromString(String instruction) {
        return Arrays.stream(values())
            .filter(i -> i.alternativeNames.contains(instruction))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unrecognized option: " + instruction));
    }

}
