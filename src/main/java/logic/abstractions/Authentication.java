package logic.abstractions;

import shared.domain.User;

/**
 * To provide a minimal level of security, quizzes
 * will be accessed only by authenticated users.<br/>
 * This interface will be used by the different implementations
 * of the authentication service.
 *
 * @see User
 */
public interface Authentication {
    /**
     * Match a name and password with a user in the database.
     *
     * @param username the name of the user.
     * @param password the password of the user.
     * @return the user that has the matching name and password or
     * <b>null</b> if not matching.
     * @see User
     */
    User signIn(String username, String password);

    /**
     * Create a user with the specified name and password.
     *
     * @param username the name of the user.
     * @param password the password of the user.
     * @return the created user or <b>null</b> if could not create.
     * @see User
     */
    User signUp(String username, String password);
}
