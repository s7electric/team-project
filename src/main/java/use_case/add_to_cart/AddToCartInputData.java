package use_case.add_to_cart;

/**
 * The Input Data for the Add To Cart Use Case.
 */
public class AddToCartInputData {
    private String username;
    private String productUUID;
    private int quantity;

    public AddToCartInputData(String user, String productId, int quantity) {
        this.username = user;
        this.productUUID = productId;
        this.quantity = quantity;
    }
    public String getUser() {
        return username;
    }
    public String getProductUUID() {
        return productUUID;
    }
    public int getQuantity() {
        return quantity;
    }

}
