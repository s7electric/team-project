package interface_adapter.checkout;

import entity.CartItemDisplay;
import java.util.List;

/**
 * ViewModel for the checkout presentation layer
 * Contains formatted data ready for display
 */
public class CheckoutViewModel {
    private final String username;
    private final String email;
    private final String billingAddress;
    private final List<CartItemDisplay> cartItems;
    private final String formattedSubtotal;
    private final String formattedTotalItems;
    private final String formattedPointsDiscount;
    private final String formattedTotalAfterDiscount;
    private final String formattedUserBalance;
    private final String formattedUserPoints;
    private final String formattedAmountFromBalance;
    private final String formattedBalanceAfterPayment;
    private final boolean hasSufficientFunds;
    private final String paymentStatusMessage;
    private final String paymentStatusColor;

    public CheckoutViewModel(String username, String email, String billingAddress,
                             List<CartItemDisplay> cartItems, String formattedSubtotal,
                             String formattedTotalItems, String formattedPointsDiscount,
                             String formattedTotalAfterDiscount, String formattedUserBalance,
                             String formattedUserPoints, String formattedAmountFromBalance,
                             String formattedBalanceAfterPayment, boolean hasSufficientFunds,
                             String paymentStatusMessage, String paymentStatusColor) {
        this.username = username;
        this.email = email;
        this.billingAddress = billingAddress;
        this.cartItems = cartItems;
        this.formattedSubtotal = formattedSubtotal;
        this.formattedTotalItems = formattedTotalItems;
        this.formattedPointsDiscount = formattedPointsDiscount;
        this.formattedTotalAfterDiscount = formattedTotalAfterDiscount;
        this.formattedUserBalance = formattedUserBalance;
        this.formattedUserPoints = formattedUserPoints;
        this.formattedAmountFromBalance = formattedAmountFromBalance;
        this.formattedBalanceAfterPayment = formattedBalanceAfterPayment;
        this.hasSufficientFunds = hasSufficientFunds;
        this.paymentStatusMessage = paymentStatusMessage;
        this.paymentStatusColor = paymentStatusColor;
    }

    // Getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getBillingAddress() { return billingAddress; }
    public List<CartItemDisplay> getCartItems() { return cartItems; }
    public String getFormattedSubtotal() { return formattedSubtotal; }
    public String getFormattedTotalItems() { return formattedTotalItems; }
    public String getFormattedPointsDiscount() { return formattedPointsDiscount; }
    public String getFormattedTotalAfterDiscount() { return formattedTotalAfterDiscount; }
    public String getFormattedUserBalance() { return formattedUserBalance; }
    public String getFormattedUserPoints() { return formattedUserPoints; }
    public String getFormattedAmountFromBalance() { return formattedAmountFromBalance; }
    public String getFormattedBalanceAfterPayment() { return formattedBalanceAfterPayment; }
    public boolean hasSufficientFunds() { return hasSufficientFunds; }
    public String getPaymentStatusMessage() { return paymentStatusMessage; }
    public String getPaymentStatusColor() { return paymentStatusColor; }
}