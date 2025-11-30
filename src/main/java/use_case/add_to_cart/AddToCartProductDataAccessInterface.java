package use_case.add_to_cart;

import entity.Product;

public interface AddToCartProductDataAccessInterface {
    /**
     * Gets the product from the database to be displayed
     * @param productUUID the product to look for
     */
    Product getProduct(String productUUID);
}
