package use_case.make_listing;

import entity.Product;

public class MakeListingOutputData {
    private final String message;
    private String username;

    public MakeListingOutputData(String message, String username) {
        this.message = message;
        this.username = username;
    }
    public String getMessage() {
        return message;
    }
    public String getUsername() {
        return username;
    }
}