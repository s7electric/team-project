package test;

import view.OrderConfirmationWindow;
import use_case.checkout.CheckoutOutputData;
import entity.CartItemDisplay;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class OrderConfirmationTest {
    public static void main(String[] args) {
        // Create sample cart items
        List<CartItemDisplay> cartItems = Arrays.asList(
                new CartItemDisplay("Wireless Bluetooth Headphones", 79.99, 1, 79.99),
                new CartItemDisplay("USB-C Charging Cable", 19.99, 2, 39.98),
                new CartItemDisplay("Phone Case - Black", 14.99, 1, 14.99),
                new CartItemDisplay("Screen Protector", 9.99, 3, 29.97),
                new CartItemDisplay("Laptop Stand", 49.99, 1, 49.99)
        );

        double totalPrice = 79.99 + 39.98 + 14.99 + 29.97 + 49.99; // 214.92
        int totalItems = 1 + 2 + 1 + 3 + 1; // 8 items

        // Create the output data
        CheckoutOutputData outputData = new CheckoutOutputData(
                "john_doe123",
                "john.doe@example.com",
                "123 Main Street, Apt 4B\nNew York, NY 10001\nUnited States",
                cartItems,
                totalPrice,
                totalItems
        );


        SwingUtilities.invokeLater(() -> {
            OrderConfirmationWindow window = new OrderConfirmationWindow(outputData);
            window.setVisible(true);
        });
    }
}