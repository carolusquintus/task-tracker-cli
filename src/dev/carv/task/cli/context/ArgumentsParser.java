package dev.carv.task.cli.context;

import dev.carv.task.cli.domain.Option;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map.Entry;

public class ArgumentsParser {

    private final String[] arguments;

    public ArgumentsParser(String[] args) {
        this.arguments = args;
    }

    public Entry<Option, List<String>> parse() {
        var option = Option.fromString(arguments[0]);
        var params = List.of(arguments).subList(1, arguments.length);
        return new SimpleImmutableEntry<>(option, params);
    }

}
