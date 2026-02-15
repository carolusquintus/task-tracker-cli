package dev.carv.task.cli.context;

import dev.carv.task.cli.domain.Instruction;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map.Entry;

public class ArgumentParser {

    private final String[] arguments;

    public ArgumentParser(String[] args) {
        this.arguments = args;
    }

    public Entry<Instruction, List<String>> parse() {
        var instruction = Instruction.fromString(arguments[0]);
        var params = List.of(arguments).subList(1, arguments.length);
        return new SimpleImmutableEntry<>(instruction, params);
    }

}
