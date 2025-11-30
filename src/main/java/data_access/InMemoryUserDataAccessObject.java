package data_access;

import entity.User;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.sign_up.SignUpDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple in-memory DAO for development/tests. Not persisted between runs.
 */
public class InMemoryUserDataAccessObject implements
        LoginUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        SignUpDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private String currentUsername;

    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void createUser(User user) {
        save(user);
    }

    @Override
    public boolean checkUserExists(String username) {
        return existsByName(username);
    }
}
