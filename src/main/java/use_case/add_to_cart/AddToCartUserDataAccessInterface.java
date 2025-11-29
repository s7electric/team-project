package use_case.add_to_cart;

import entity.User;

public interface AddToCartUserDataAccessInterface {

    /**
     * Checks if the given user exists.
     * @param username the user to look for
     */
    User getUserData(String username);

    void addToCart(User username, Integer productid, Integer quantity);
}
