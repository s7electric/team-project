package test;

import entity.User;
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

        double subtotal = 79.99 + 39.98 + 14.99 + 29.97 + 49.99; // 214.92
        int totalItems = 1 + 2 + 1 + 3 + 1; // 8 items

        // Calculate payment details (mimicking what the use case would do)
        double userBalance = 150.00; // Example user balance
        int userPoints = 2500;       // Example user points
        double pointsDiscount = calculatePointsDiscount(userPoints); // $20 discount (2 * $10)
        double totalAfterDiscount = Math.max(0, subtotal - pointsDiscount); // 214.92 - 20 = 194.92
        double amountFromBalance = Math.min(totalAfterDiscount, userBalance); // 150.00 (since balance < total)
        double balanceAfterPayment = userBalance - amountFromBalance; // 150 - 150 = 0
        boolean hasSufficientFunds = balanceAfterPayment >= 0; // true

        // Create the output data with all required parameters
        CheckoutOutputData outputData = new CheckoutOutputData(
                "john_doe123",
                "john.doe@example.com",
                "123 Main Street, Apt 4B\nNew York, NY 10001\nUnited States",
                cartItems,
                subtotal,                    // subtotal
                totalItems,                  // totalItems
                pointsDiscount,              // pointsDiscount
                totalAfterDiscount,          // totalAfterDiscount
                userBalance,                 // userBalance
                userPoints,                  // userPoints
                amountFromBalance,           // amountFromBalance
                balanceAfterPayment,         // balanceAfterPayment
                hasSufficientFunds           // hasSufficientFunds
        );

        // Create user with balance and points
        User user = new User("john_doe123", "john.doe@example.com", "wordpass12", "123 Main Street");
        user.addBalance(userBalance);
        user.addPointsBalance(userPoints);

        SwingUtilities.invokeLater(() -> {
            OrderConfirmationWindow window = new OrderConfirmationWindow(outputData, user);
            window.setVisible(true);
        });
    }

    private static double calculatePointsDiscount(int points) {
        return (double) (points - (points % 1000)) / 10;
    }
}