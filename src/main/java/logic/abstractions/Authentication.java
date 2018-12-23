package logic.abstractions;

import shared.domain.User;

public interface Authentication {
    User login(String username, String password);
    User register(String username, String password);
}
