package interface_adapter.checkout;

import use_case.checkout.CheckoutOutputBoundary;
import use_case.checkout.CheckoutOutputData;
import interface_adapter.checkout.CheckoutState;

import java.awt.*;

/**
 * Presenter for the checkout use case
 * Manages multiple view interfaces
 */
public class CheckoutPresenter implements CheckoutOutputBoundary {
    private OrderConfirmationView orderConfirmationView;
    private PaymentView paymentView;
    private CheckoutState currentState;

    public void setOrderConfirmationView(OrderConfirmationView view) {
        this.orderConfirmationView = view;
    }

    public void setPaymentView(PaymentView view) {
        this.paymentView = view;
    }

    @Override
    public void presentOrderConfirmation(CheckoutOutputData outputData) {
        this.currentState = new CheckoutState(
                outputData.getUsername(),
                outputData.getEmail(),
                outputData.getBillingAddress(),
                outputData.getCartItems(),
                outputData.getSubtotal(),
                outputData.getTotalItems(),
                outputData.getPointsDiscount(),
                outputData.getTotalAfterDiscount(),
                outputData.getUserBalance(),
                outputData.getUserPoints(),
                outputData.getAmountFromBalance(),
                outputData.getBalanceAfterPayment(),
                outputData.hasSufficientFunds()
        );

        CheckoutViewModel orderConfirmationViewModel = createOrderConfirmationViewModel(currentState);

        if (orderConfirmationView != null) {
            orderConfirmationView.showOrderConfirmation(orderConfirmationViewModel);
        }
    }

    @Override
    public void presentPaymentScreen() {
        if (currentState != null && paymentView != null) {
            CheckoutViewModel paymentViewModel = createPaymentViewModel(currentState);
            paymentView.showPaymentScreen(paymentViewModel);
        }
    }

    @Override
    public void presentPaymentResult(boolean success, String message) {
        if (paymentView != null) {
            paymentView.showPaymentResult(success, message);
        }
    }

    @Override
    public void presentCheckoutError(String errorMessage) {
        // Show error in the appropriate view
        if (orderConfirmationView != null) {
            orderConfirmationView.showError(errorMessage);
        }
        if (paymentView != null) {
            paymentView.showError(errorMessage);
        }
    }


    private CheckoutViewModel createOrderConfirmationViewModel(CheckoutState state) {
        return new CheckoutViewModel(
                state.getUsername(),
                state.getEmail(),
                state.getBillingAddress(),
                state.getCartItems(),
                String.format("$%.2f", state.getSubtotal()),
                String.valueOf(state.getTotalItems()),
                String.format("-$%.2f", state.getPointsDiscount()),
                String.format("$%.2f", state.getTotalAfterDiscount()),
                String.format("$%.2f", state.getUserBalance()),
                String.valueOf(state.getUserPoints()),
                String.format("$%.2f", state.getAmountFromBalance()),
                String.format("$%.2f", state.getBalanceAfterPayment()),
                state.hasSufficientFunds(),
                "Review your order details",
                "BLACK"
        );
    }

    private CheckoutViewModel createPaymentViewModel(CheckoutState state) {
        String statusMessage = state.hasSufficientFunds() ?
                "Ready to complete payment" : "Insufficient funds";
        String statusColor = state.hasSufficientFunds() ? "BLUE" : "RED";

        return new CheckoutViewModel(
                state.getUsername(),
                state.getEmail(),
                state.getBillingAddress(),
                state.getCartItems(),
                String.format("$%.2f", state.getSubtotal()),
                String.valueOf(state.getTotalItems()),
                String.format("-$%.2f", state.getPointsDiscount()),
                String.format("$%.2f", state.getTotalAfterDiscount()),
                String.format("$%.2f", state.getUserBalance()),
                String.valueOf(state.getUserPoints()),
                String.format("$%.2f", state.getAmountFromBalance()),
                String.format("$%.2f", state.getBalanceAfterPayment()),
                state.hasSufficientFunds(),
                statusMessage,
                statusColor
        );
    }

    public CheckoutState getCurrentState() {
        return currentState;
    }
}