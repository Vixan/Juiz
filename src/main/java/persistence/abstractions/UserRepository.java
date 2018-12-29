package persistence.abstractions;

import shared.domain.User;
import javax.persistence.Entity;

/**
 * The User {@link Entity} repository interface with custom behaviour.
 *
 * @see Repository
 * @see User
 */
public interface UserRepository extends Repository<User> {
    /**
     * @param userName the name to retrieve the user by.
     * @return the user queried.
     */
    User getByName(String userName);
}
