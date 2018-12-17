package logic.abstractions;

import java.util.List;

public interface Service<T> {
    T getById(Integer entityId);
    List<T> getAll();

    void add(T entity);
    void update(T entity);
    void delete(Integer entityId);
}
