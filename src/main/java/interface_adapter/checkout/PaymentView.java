package interface_adapter.checkout;

/**
 * View interface specifically for payment
 */
public interface PaymentView {
    void showPaymentScreen(CheckoutViewModel viewModel);
    void showPaymentResult(boolean success, String message);
    void showError(String errorMessage);
}