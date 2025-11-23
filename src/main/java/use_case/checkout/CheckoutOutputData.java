package use_case.checkout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

import entity.*;


/**
 * Order Confirmation Window that displays user details, billing address,
 * cart items, and total price
 */
public class CheckoutOutputData {
    private final String username;
    private final String email;
    private final String billingAddress;
    private final List cartItems;
    private final double totalPrice;
    private final int totalItems;

    public CheckoutOutputData(String username, String email, String billingAddress,
                              List cartItems, double totalPrice, int totalItems) {
        this.username = username;
        this.email = email;
        this.billingAddress = billingAddress;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
    }

    // Getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getBillingAddress() { return billingAddress; }
    public List getCartItems() { return cartItems; }
    public double getTotalPrice() { return totalPrice; }
    public int getTotalItems() { return totalItems; }
}