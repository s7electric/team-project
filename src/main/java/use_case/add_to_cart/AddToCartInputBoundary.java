package use_case.add_to_cart;

/**
 * Input Boundary for actions which are related to adding items to cart.
 */
public interface AddToCartInputBoundary {
    /**
     * Executes the add to cart iuse case.
     * @param addToCartInputData the input data
     */
    void execute(AddToCartInputData addToCartInputData);
}
