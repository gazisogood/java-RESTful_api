package ru.school21.edu.backend.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, K, ID> {
    void save(T entity);

    void delete(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void update(T entity, K data);
}
