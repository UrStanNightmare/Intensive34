package ru.aston.oshchepkov_aa.task4;

import java.util.List;
import java.util.Optional;

public interface DaoDataEntityLayer<T> {
    List<T> findAll();
    Optional<T> findById(int id);
    boolean deleteById(int id);
    boolean create(T t);
    Optional<T> update(T t);
}
