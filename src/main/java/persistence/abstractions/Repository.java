package persistence.abstractions;

import java.util.Collection;

public interface Repository<T> {
    Collection<T> getAll();
    T getById(Integer id);
    void add(T entity);
    void update(T entity);
    void delete(T entity);
}
