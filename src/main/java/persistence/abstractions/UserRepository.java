package persistence.abstractions;

import shared.domain.User;

public interface UserRepository extends Repository<User> {
    User getByName(String userName);
}
