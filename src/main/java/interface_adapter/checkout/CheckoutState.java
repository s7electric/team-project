package interface_adapter.checkout;

import entity.CartItemDisplay;
import java.util.List;

/**
 * State object that holds the current state of the checkout process
 */
public class CheckoutState {
    private final String username;
    private final String email;
    private final String billingAddress;
    private final List<CartItemDisplay> cartItems;
    private final double subtotal;
    private final int totalItems;
    private final double pointsDiscount;
    private final double totalAfterDiscount;
    private final double userBalance;
    private final int userPoints;
    private final double amountFromBalance;
    private final double balanceAfterPayment;
    private final boolean hasSufficientFunds;

    public CheckoutState(String username, String email, String billingAddress,
                         List<CartItemDisplay> cartItems, double subtotal, int totalItems,
                         double pointsDiscount, double totalAfterDiscount, double userBalance,
                         int userPoints, double amountFromBalance, double balanceAfterPayment,
                         boolean hasSufficientFunds) {
        this.username = username;
        this.email = email;
        this.billingAddress = billingAddress;
        this.cartItems = cartItems;
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

    // Getters (remove getUserId)
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getBillingAddress() { return billingAddress; }
    public List<CartItemDisplay> getCartItems() { return cartItems; }
    public double getSubtotal() { return subtotal; }
    public int getTotalItems() { return totalItems; }
    public double getPointsDiscount() { return pointsDiscount; }
    public double getTotalAfterDiscount() { return totalAfterDiscount; }
    public double getUserBalance() { return userBalance; }
    public int getUserPoints() { return userPoints; }
    public double getAmountFromBalance() { return amountFromBalance; }
    public double getBalanceAfterPayment() { return balanceAfterPayment; }
    public boolean hasSufficientFunds() { return hasSufficientFunds; }
}