package dev.carv.task.cli.repository;

import dev.carv.task.cli.domain.Task;

public class TaskRepository implements Repository<Task, Integer> {

    @Override
    public Integer insert(Task type) {
        return 0;
    }

}
