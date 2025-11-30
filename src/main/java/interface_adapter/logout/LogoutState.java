package interface_adapter.logout;

public class LogoutState {
    private String username;
    private String errorMessage;

    public LogoutState() {
        this("", null, false);
    }

    public LogoutState(String username, String errorMessage, boolean loggedIn) {
        this.username = username;
        this.errorMessage = errorMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}

