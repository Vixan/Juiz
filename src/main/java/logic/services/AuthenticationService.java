package logic.services;

import logic.abstractions.Authentication;
import org.mindrot.jbcrypt.BCrypt;
import shared.domain.User;

/**
 * A particular authentication service implementation that uses
 * the {@link UserService} to retrieve and persist users.
 *
 * @see Authentication
 */
public class AuthenticationService implements Authentication {
    private UserService userService = new UserService();

    /**
     * Match a name and password with a user in the database.
     * If a user with the searched name has been found, the password
     * is tested using the {@link BCrypt} implementation of the BCrypt
     * hashing algorithm.
     *
     * @param username the name of the user.
     * @param password the password of the user.
     * @return the user that has the matching name and password or
     * <b>null</b> if not matching.
     */
    @Override
    public User signIn(String username, String password) {
        User user = userService.getByName(username);

        if (user == null) {
            return null;
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            return null;
        }

        return user;
    }

    /**
     * Create a user with the specified name and password.
     * The password is hashed using the {@link BCrypt} implementation
     * of the BCrypt hashing algorithm before being stored in the database.
     *
     * @param username the name of the user.
     * @param password the password of the user.
     * @return the created user or <b>null</b> if could not create.
     * @see User
     */
    @Override
    public User signUp(String username, String password) {
        User user = new User();
        user.setName(username);

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(hashedPassword);

        userService.add(user);

        return user;
    }
}
