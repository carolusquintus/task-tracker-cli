package dev.carv.task.cli.repository;

import java.util.List;

public interface Repository<T, K> {

    T save(T type);

    List<T> findAll();

}
