package interface_adapter.make_listing;

public class MakeListingState {
    private String username;

    public MakeListingState() {
        this("");
    }

    MakeListingState(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}