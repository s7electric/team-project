package interface_adapter.logged_in;

public class LoggedInState {
    private String username;

    public LoggedInState(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}