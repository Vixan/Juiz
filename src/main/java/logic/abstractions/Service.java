package logic.abstractions;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * The application business logic level is built using <i>Services</i>
 * Each service calls the respective repository methods to retrieve or
 * persist data.<br/>
 * This interface will be used by the particular implementations
 * for each {@link Entity}.
 *
 * @param <T> the database {@link Entity} used.
 */
public interface Service<T> {
    /**
     * Retrieve the element of the entity with a certain identifier.
     *
     * @return the entity with the matching {@link Id} or <b>null</b>.
     * @see Entity
     */
    T getById(Integer entityId);

    /**
     * Retrieve all elements of the entity.
     *
     * @return the list of queried entity elements.
     * @see Entity
     */
    List<T> getAll();

    /**
     * Add the entity element in the database.
     *
     * @see Entity
     */
    void add(T entity);

    /**
     * Update the entity element data in the database.
     *
     * @see Entity
     */
    void update(T entity);

    /**
     * Delete the entity element from the database.
     *
     * @see Entity
     */
    void delete(Integer entityId);
}
