package use_case.add_to_cart;

import entity.User;

/**
 * The Input Data for the Add To Cart Use Case.
 */
public class AddToCartInputData {
    private String username;
    private int productId;
    private int quantity;

    public AddToCartInputData(String user, int productId, int quantity) {
        this.username = user;
        this.productId = productId;
        this.quantity = quantity;
    }
    public String getUser() {
        return username;
    }
    public int getProductId() {
        return productId;
    }
    public int getQuantity() {
        return quantity;
    }

}
