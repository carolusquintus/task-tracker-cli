package dev.carv.task.cli.repository;

import java.util.List;

public interface Repository<T, K> {

    T save(T type);

    void update(T type);

    T findById(K id);

    List<T> findAll();

    List<T> findAllByStatus(String status);

}
