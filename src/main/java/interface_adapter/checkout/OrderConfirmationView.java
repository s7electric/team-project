package interface_adapter.checkout;

/**
 * View interface specifically for order confirmation
 */
public interface OrderConfirmationView {
    void showOrderConfirmation(CheckoutViewModel viewModel);
    void showError(String errorMessage);
}