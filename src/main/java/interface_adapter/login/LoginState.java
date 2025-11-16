package interface_adapter.login;

public class LoginState {
    private String username;
    private String errorMessage;
    private boolean loggedIn;

    public LoginState() {
        this("", null, false);
    }

    public LoginState(String username, String errorMessage, boolean loggedIn) {
        this.username = username;
        this.errorMessage = errorMessage;
        this.loggedIn = loggedIn;
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }


}
