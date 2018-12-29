package logic.services;

import logic.abstractions.Service;
import persistence.hibernate.HbnUserRepository;
import shared.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides methods for communication with
 * the Hibernate {@link User} repository.
 *
 * @see Service
 */
public class UserService implements Service<User> {
    private HbnUserRepository userRepository = new HbnUserRepository();

    @Override
    public User getById(Integer userId) {
        userRepository.openCurrentSession();
        User user = userRepository.getById(userId);
        userRepository.closeCurrentSession();

        return user;
    }

    /**
     * @param userName the name of the user to be retrieved.
     * @return the user with the searched name or <b>null</b> if
     * not found.
     */
    public User getByName(String userName) {
        userRepository.openCurrentSession();
        User user = userRepository.getByName(userName);
        userRepository.closeCurrentSession();

        return user;
    }

    @Override
    public List<User> getAll() {
        userRepository.openCurrentSession();
        List<User> users = new ArrayList<>(userRepository.getAll());
        userRepository.closeCurrentSession();

        return users;
    }

    @Override
    public void add(User user) {
        userRepository.openCurrentSessionWithTransaction();
        if (userRepository.getByName(user.getName()) == null) {
            userRepository.add(user);
        }
        userRepository.closeCurrentSessionWithTransaction();
    }

    @Override
    public void update(User user) {
        userRepository.openCurrentSessionWithTransaction();
        if (userRepository.getById(user.getId()) != null) {
            userRepository.update(user);
        }
        userRepository.closeCurrentSessionWithTransaction();
    }

    @Override
    public void delete(Integer userId) {
        userRepository.openCurrentSessionWithTransaction();
        User user = userRepository.getById(userId);
        if (user != null) {
            userRepository.delete(user);
        }
        userRepository.closeCurrentSessionWithTransaction();
    }
}
