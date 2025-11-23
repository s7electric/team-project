package use_case.open_product;

/**
 * The output boundary for the Add To Cart Use Case.
 */
public interface OpenProductOutputBoundary {
    /**
     * Prepares the success view for the Add To Cart Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(OpenProductOutputData outputData);

}
