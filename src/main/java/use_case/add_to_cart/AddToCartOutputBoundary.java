package use_case.add_to_cart;

/**
 * The output boundary for the Add To Cart Use Case.
 */
public interface AddToCartOutputBoundary {
    /**
     * Prepares the success view for the Add To Cart Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(AddToCartOutputData outputData);
    /**
     * Prepares the failure view for the Add to Cart Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailureView(String errorMessage);

}
