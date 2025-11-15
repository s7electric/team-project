package use_case.add_to_cart;

import entity.User;
import entity.Product;

public interface AddToCartUserDataAccessInterface {

    /**
     * Checks if the given user exists.
     * @param user the user to look for
     * @return true if a user with the given username exists; false otherwise
     */
    void addToCart(User user, Product product);
}
