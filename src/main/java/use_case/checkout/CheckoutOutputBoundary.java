package use_case.checkout;

/**
 * The output boundary for the Checkout Use Case.
 */
public interface CheckoutOutputBoundary {
    void presentOrderConfirmation(CheckoutOutputData outputData);

    void presentPaymentScreen();

    void presentPaymentResult(boolean success, String message);

    void presentCheckoutError(String errorMessage);
}
