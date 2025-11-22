package interface_adapter.homepage;

public class HomepageState {
    private String username;

    public HomepageState(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}