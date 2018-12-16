package logic.services;

import logic.abstractions.Service;
import persistence.hibernate.HbnUserRepository;
import shared.domain.User;

import java.util.Collection;

public class UserService implements Service<User> {
    private HbnUserRepository userRepository = new HbnUserRepository();

    public User getById(Integer userId) {
        userRepository.openCurrentSession();
        User user = userRepository.getById(userId);
        userRepository.closeCurrentSession();

        return user;
    }

    public User getByName(String userName) {
        userRepository.openCurrentSession();
        User user = userRepository.getByName(userName);
        userRepository.closeCurrentSession();

        return user;
    }

    public Collection<User> getAll() {
        return null;
    }

    public void add(User entity) {

    }

    public void update(User entity) {

    }

    public void delete(User entity) {

    }
}
