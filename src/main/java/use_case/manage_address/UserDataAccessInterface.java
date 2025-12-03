package use_case.manage_address;

import entity.User;

/**
 * Data access interface for loading and saving Users for the Manage Address use cases.
 */
public interface UserDataAccessInterface {

    /**
     * @param username the username of the user
     * @return the User, or null if not found
     */
    User getUser(String username);

    /**
     * @param user the user to save
     */
    void saveUser(User user);

    User getUserData(String username);
}