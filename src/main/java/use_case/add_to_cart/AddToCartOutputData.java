package use_case.add_to_cart;

/**
 * Output Data for the Add to Cart Use Case.
 */
public class AddToCartOutputData {

    private String productUUID;
    private String productName;
    private double cartTotal;

    public AddToCartOutputData(String productName, String productUUID, double cartTotal) {
        this.productUUID = productUUID;
        this.productName = productName;
        this.cartTotal = cartTotal;
    }

    public String getProductName() {
        return productName;
    }
    public String getProductUUID() {
        return productUUID;
    }
    public double getCartTotal() {
        return cartTotal;
    }
}
