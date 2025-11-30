package use_case.checkout;

import entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutInteractor implements CheckoutInputBoundary {
    private final CheckoutDataAccessInterface dataAccess;
    private final CheckoutOutputBoundary outputBoundary;

    public CheckoutInteractor(CheckoutDataAccessInterface dataAccess,
                              CheckoutOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(CheckoutInputData inputData) {
        try {
            // 1. Get user from data access using username as identifier
            User user = dataAccess.getUser(inputData.getUsername());

            // 2. Get user's cart
            Cart cart = user.getCart();

            // 3. Validate cart has items
            if (cart.getProducts().isEmpty()) {
                outputBoundary.presentCheckoutError("Cart is empty");
                return;
            }

            // 4. Get billing address
            String billingAddress = getDefaultBillingAddress(user);

            // 5. Prepare cart items for display
            List<CartItemDisplay> cartItemDisplays = prepareCartItemDisplays(cart);

            // 6. Calculate payment details
            PaymentCalculation paymentCalc = calculatePaymentDetails(cart, user);

            // 7. Create output data with all calculated values
            CheckoutOutputData outputData = new CheckoutOutputData(
                    user.getUsername(),  // Use username as identifier
                    user.getEmail(),
                    billingAddress,
                    cartItemDisplays,
                    paymentCalc.subtotal,
                    paymentCalc.totalItems,
                    paymentCalc.pointsDiscount,
                    paymentCalc.totalAfterDiscount,
                    paymentCalc.userBalance,
                    paymentCalc.userPoints,
                    paymentCalc.amountFromBalance,
                    paymentCalc.balanceAfterPayment,
                    paymentCalc.hasSufficientFunds
            );

            // 8. Present the order confirmation
            outputBoundary.presentOrderConfirmation(outputData);

        } catch (Exception e) {
            outputBoundary.presentCheckoutError("Checkout failed: " + e.getMessage());
        }
    }

    // ... (rest of the helper methods remain the same)
    private PaymentCalculation calculatePaymentDetails(Cart cart, User user) {
        double subtotal = calculateTotalPrice(cart);
        int totalItems = cart.getTotalQuantity();
        double userBalance = user.getBalance();
        int userPoints = user.getPointsBalance();

        double pointsDiscount = calculatePointsDiscount(userPoints);
        double totalAfterDiscount = Math.max(0, subtotal - pointsDiscount);

        double amountFromBalance = Math.min(totalAfterDiscount, userBalance);
        double balanceAfterPayment = userBalance - amountFromBalance;
        boolean hasSufficientFunds = balanceAfterPayment >= 0;

        return new PaymentCalculation(
                subtotal, totalItems, pointsDiscount, totalAfterDiscount,
                userBalance, userPoints, amountFromBalance, balanceAfterPayment, hasSufficientFunds
        );
    }

    private double calculatePointsDiscount(int points) {
        int discountMultiplier = points / 1000;
        return discountMultiplier * 10.0;
    }

    private String getDefaultBillingAddress(User user) {
        for (Address address : user.getBillingAddresses()) {
            if (address.isDefaultBilling()) {
                return address.toSingleLine();
            }
        }
        return user.getBillingAddresses().isEmpty() ?
                "No billing address available" :
                user.getBillingAddresses().get(0).toSingleLine();
    }

    private List<CartItemDisplay> prepareCartItemDisplays(Cart cart) {
        List<CartItemDisplay> displays = new ArrayList<>();
        Map<String, CartItem> products = cart.getProducts();

        for (CartItem item : products.values()) {
            Product product = item.getProduct();
            displays.add(new CartItemDisplay(
                    product.getName(),
                    product.getPrice(),
                    item.getQuantity(),
                    product.getPrice() * item.getQuantity()
            ));
        }
        return displays;
    }

    private double calculateTotalPrice(Cart cart) {
        double total = 0.0;
        Map<String, CartItem> products = cart.getProducts();
        for (CartItem item : products.values()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    private static class PaymentCalculation {
        final double subtotal;
        final int totalItems;
        final double pointsDiscount;
        final double totalAfterDiscount;
        final double userBalance;
        final int userPoints;
        final double amountFromBalance;
        final double balanceAfterPayment;
        final boolean hasSufficientFunds;

        PaymentCalculation(double subtotal, int totalItems, double pointsDiscount,
                           double totalAfterDiscount, double userBalance, int userPoints,
                           double amountFromBalance, double balanceAfterPayment,
                           boolean hasSufficientFunds) {
            this.subtotal = subtotal;
            this.totalItems = totalItems;
            this.pointsDiscount = pointsDiscount;
            this.totalAfterDiscount = totalAfterDiscount;
            this.userBalance = userBalance;
            this.userPoints = userPoints;
            this.amountFromBalance = amountFromBalance;
            this.balanceAfterPayment = balanceAfterPayment;
            this.hasSufficientFunds = hasSufficientFunds;
        }
    }
}