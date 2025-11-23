package use_case.open_product;

/**
 * Input Boundary for actions which are related to adding items to cart.
 */
public interface OpenProductInputBoundary {
    /**
     * Executes the add to cart iuse case.
     * @param openProductInputData the input data
     */
    void execute(OpenProductInputData openProductInputData);
}
