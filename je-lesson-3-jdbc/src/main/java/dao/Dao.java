package dao;

import entities.City;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void create(T entity);
    Optional<T> readById(long id);
    List<T> readByName(String name);
    void update(T entity);
    void delete(long id);
}