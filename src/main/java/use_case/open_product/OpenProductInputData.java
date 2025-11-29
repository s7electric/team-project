package use_case.open_product;

import entity.User;

/**
 * The Input Data for the Add To Cart Use Case.
 */
public class OpenProductInputData {
    private String productId;
    private String username;

    public OpenProductInputData(String productId, String username) {
        this.productId = productId;
        this.username = username;
    }
    public String getProductId() {
        return productId;
    }
    public String getUsername() {
        return username;
    }
}
