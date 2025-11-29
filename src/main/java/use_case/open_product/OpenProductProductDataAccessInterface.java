package use_case.open_product;

import entity.Product;
import entity.User;

public interface OpenProductProductDataAccessInterface {

    /**
     * Gets the product from the database to be displayed
     * @param productUUID the product to look for
     */
    Product getProduct(String productUUID);
}
