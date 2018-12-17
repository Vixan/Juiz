package persistence.abstractions;

import java.util.List;

public interface Repository<T> {
    List<T> getAll();
    T getById(Integer id);
    void add(T entity);
    void update(T entity);
    void delete(T entity);
}
