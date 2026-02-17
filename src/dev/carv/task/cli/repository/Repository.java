package dev.carv.task.cli.repository;

public interface Repository<T, K> {

    K insert(T type);

}
