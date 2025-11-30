package use_case.sign_up;

import entity.User;

/**
 * This interface says its implementing class to have methods for searching a user and creating one
 * */
public interface SignUpDataAccessInterface {
    /**
     * Creates a user in the database
     * @param user the new user
     * */
    void createUser(User user);

    /**
     * Checks if the user exists from before
     * @param username the name of the user
     * */
    boolean checkUserExists(String username);

    /**
     * Persists the username of the currently signed-in user so other flows know the session owner.
     * @param username the username to mark as current (or null to clear)
     */
    void setCurrentUsername(String username);
}
