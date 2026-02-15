package dev.carv.task.cli;

import dev.carv.task.cli.context.Invoker;

public class Main {

    static void main(String[] args) {
        try {
            new Invoker(args).invoke();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

}
