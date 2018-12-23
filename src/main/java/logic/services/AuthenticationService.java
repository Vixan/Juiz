package logic.services;

import logic.abstractions.Authentication;
import org.mindrot.jbcrypt.BCrypt;
import shared.domain.User;

public class AuthenticationService implements Authentication  {
    private UserService userService = new UserService();

    @Override
    public User login(String username, String password) {
        User user = userService.getByName(username);

        if (user == null) {
            return null;
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            return null;
        }

        return user;
    }

    @Override
    public User register(String username, String password) {
        User user = new User();
        user.setName(username);

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(hashedPassword);

        userService.add(user);

        return user;
    }
}
