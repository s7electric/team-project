package use_case.sign_up;

import entity.User;

/**
 * This interface says its implementing class to have methods for searching a user and creating one
 * */
public interface SignUpDataAccessInterface {
    /**
     * Creates a user in the database
     * @param user the new user
     * @param rawPassword the user's plaintext password (needed for external auth providers)
     * */
    void createUser(User user, String rawPassword);

    /**
     * Checks if the user exists from before
     * @param username the name of the user
     * */
    boolean checkUserExists(String username);
}
