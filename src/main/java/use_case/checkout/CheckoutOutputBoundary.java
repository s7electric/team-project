package use_case.checkout;

/**
 * The output boundary for the Login Use Case.
 */
public interface CheckoutOutputBoundary {
    void presentOrderConfirmation(CheckoutOutputData outputData);
    void presentCheckoutError(String errorMessage);
}
