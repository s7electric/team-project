package use_case.open_product;

import entity.User;

/**
 * The Input Data for the Add To Cart Use Case.
 */
public class OpenProductInputData {
    private int productId;

    public OpenProductInputData(int productId) {
        this.productId = productId;
    }
    public int getProductId() {
        return productId;
    }

}
