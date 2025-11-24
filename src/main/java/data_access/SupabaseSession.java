package data_access;

import use_case.logout.LogoutUserDataAccessInterface;

/**
 * Simple session holder for Supabase auth.
 */
public class SupabaseSession implements LogoutUserDataAccessInterface {

    private String accessToken;
    private String currentUsername;
    private String currentUserId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public void clear() {
        this.accessToken = null;
        this.currentUsername = null;
        this.currentUserId = null;
    }
}
