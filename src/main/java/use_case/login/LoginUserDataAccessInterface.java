package use_case.login;

import entity.User;

public interface LoginUserDataAccessInterface {
    boolean existsByName(String username);
    User get(String username);
}
