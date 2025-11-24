package use_case.make_listing;

import entity.Product;

/**
 * The input data from making a listing.
 */
public class MakeListingInputData {
    private final Product product;

    public MakeListingInputData(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
