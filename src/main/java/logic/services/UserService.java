package logic.services;

import logic.abstractions.Service;
import persistence.hibernate.HbnUserRepository;
import shared.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public List<User> getAll() {
        userRepository.openCurrentSession();
        List<User> users = new ArrayList<>(userRepository.getAll());
        userRepository.closeCurrentSession();

        return users;
    }

    public void add(User user) {
        userRepository.openCurrentSessionWithTransaction();
        if (userRepository.getByName(user.getName()) == null) {
            userRepository.add(user);
        }
        userRepository.closeCurrentSessionWithTransaction();
    }

    public void update(User user) {
        userRepository.openCurrentSessionWithTransaction();
        if (userRepository.getById(user.getId()) != null) {
            userRepository.update(user);
        }
        userRepository.closeCurrentSessionWithTransaction();
    }

    public void delete(Integer userId) {
        userRepository.openCurrentSessionWithTransaction();
        User user = userRepository.getById(userId);
        if (user != null) {
            userRepository.delete(user);
        }
        userRepository.closeCurrentSessionWithTransaction();
    }
}
