package use_case.login;

import entity.User;

public interface LoginUserDataAccessInterface {
    boolean existsByName(String username);
    User get(String username);

    /**
     * Tracks the currently logged-in username for session-sensitive flows.
     */
    void setCurrentUsername(String username);

    /**
     * Returns the currently logged-in username, or null if none.
     */
    String getCurrentUsername();
}
