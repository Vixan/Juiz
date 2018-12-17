package logic.abstractions;

import java.util.Collection;

public interface Service<T> {
    T getById(Integer entityId);
    Collection<T> getAll();

    void add(T entity);
    void update(T entity);
    void delete(Integer entity);
}
