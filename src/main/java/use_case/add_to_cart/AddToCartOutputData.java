package use_case.add_to_cart;

/**
 * Output Data for the Add to Cart Use Case.
 */
public class AddToCartOutputData {

    private String productUUID;
    private String productName;
    private Integer cartTotal;

    public AddToCartOutputData(String productName, String productUUID, Integer cartTotal) {
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
    public Integer getCartTotal() {
        return cartTotal;
    }
}
