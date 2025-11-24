package use_case.login;

public interface LoginUserDataAccessInterface {
    /**
     * Attempts to authenticate the user with the provided credentials.
     * @return true if authentication succeeds, false otherwise
     */
    boolean authenticate(String username, String password);

    /**
     * Returns the username (or email/display name) of the last authenticated user, if available.
     */
    String getCurrentUsername();
}
