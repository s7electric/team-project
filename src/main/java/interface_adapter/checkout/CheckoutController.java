package interface_adapter.checkout;

import use_case.checkout.CheckoutInputBoundary;
import use_case.checkout.CheckoutInputData;

/**
 * Controller for the checkout use case
 */
public class CheckoutController {
    private final CheckoutInputBoundary checkoutInteractor;

    public CheckoutController(CheckoutInputBoundary checkoutInteractor) {
        this.checkoutInteractor = checkoutInteractor;
    }

    public void executeCheckout(String username) {  // Changed parameter name
        CheckoutInputData inputData = new CheckoutInputData(username);
        checkoutInteractor.execute(inputData);
    }
}