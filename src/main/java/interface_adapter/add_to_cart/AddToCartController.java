package interface_adapter.add_to_cart;

import use_case.add_to_cart.AddToCartInputBoundary;
import use_case.add_to_cart.AddToCartInputData;

public class AddToCartController {
    private final AddToCartInputBoundary userAddToCartInteractor;
    public AddToCartController(AddToCartInputBoundary userAddToCartInteractor) {
        this.userAddToCartInteractor = userAddToCartInteractor;
    }

    /**
     * Executes the Add To Cart Use Case.
     * @param productUUID the product to add
     * @param quantity the quantity to add
     * @param username the user who is adding to their cart
     */
    public void execute(String productUUID, int quantity, String username){
        final AddToCartInputData addToCartInputData = new AddToCartInputData(username, productUUID,quantity);

        userAddToCartInteractor.execute(addToCartInputData);
    }
}
