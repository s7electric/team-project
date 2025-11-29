package test;

import use_case.checkout.*;
import interface_adapter.checkout.CheckoutController;
import interface_adapter.checkout.CheckoutPresenter;
import view.OrderConfirmationWindow;

import javax.swing.*;

public class OrderConfirmationTest {
    public static void main(String[] args) {
        // Set up the complete Clean Architecture stack
        CheckoutDataAccessInterface dataAccess = new MockCheckoutDataAccess();
        CheckoutPresenter presenter = new CheckoutPresenter();
        CheckoutInputBoundary interactor = new CheckoutInteractor(dataAccess, presenter);
        CheckoutController controller = new CheckoutController(interactor);

        // Create the view with the presenter
        OrderConfirmationWindow orderWindow = new OrderConfirmationWindow(presenter);

        // Execute checkout - this will trigger the whole flow
        SwingUtilities.invokeLater(() -> {
            controller.executeCheckout("john_doe");
        });
    }
}