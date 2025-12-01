package use_case.make_listing;

import entity.Product;
import entity.User;

public interface MakeListingDataAccessInterface {
    void postListing(Product product);
    User getUser(String username);
}