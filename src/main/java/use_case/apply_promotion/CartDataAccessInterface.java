package use_case.apply_promotion;

import entity.Cart;

/**
 * Data access interface for retrieving and saving carts.
 */
public interface CartDataAccessInterface {
    Cart getCartByUsername(String username);

    void saveCart(String username, Cart cart);
}