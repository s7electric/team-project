package interface_adapter.HomePagLoggedIN;

/**
 * State for the logged-in view.
 */
public class HomePageLoggedInState {
    private String username;

    public HomePageLoggedInState() {
        this("");
    }

    public HomePageLoggedInState(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
