package persistence.hibernate;

import persistence.abstractions.UserRepository;
import shared.domain.User;

public class HbnUserRepository extends HbnRepository<User> implements UserRepository {
    public HbnUserRepository() {
        super(User.class);
    }
}