package dev.carv.task.cli.repository;

import java.util.List;

public interface Repository<T, K> {

    K insert(T type);

    List<T> getAll();

}
