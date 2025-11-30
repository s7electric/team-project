package test;

import use_case.checkout.CheckoutDataAccessInterface;
import entity.*;

public class MockCheckoutDataAccess implements CheckoutDataAccessInterface {
    private final double userBalance;
    private final int userPoints;

    public MockCheckoutDataAccess() {
        this.userBalance = 150.00;
        this.userPoints = 2500;
    }

    public MockCheckoutDataAccess(double balance, int points) {
        this.userBalance = balance;
        this.userPoints = points;
    }

    @Override
    public User getUser(String username) {  // Changed parameter to username
        // Create a seller for the products
        User seller = new User("tech_store", "store@tech.com", "password", "456 Store Ave");

        // Create sample products
        Product product1 = new Product("Wireless Bluetooth Headphones", 79.99, "1",
                "headphones.jpg", seller, "Electronics");
        Product product2 = new Product("USB-C Charging Cable", 19.99, "2",
                "cable.jpg", seller, "Electronics");
        Product product3 = new Product("Phone Case - Black", 14.99, "3",
                "case.jpg", seller, "Accessories");

        // Create user with the provided username
        User user = new User(username, username + "@example.com", "password", "123 Main St");

        // Set user balance and points
        user.addBalance(userBalance);
        user.addPointsBalance(userPoints);

        // Add additional address as default billing
        Address billingAddress = new Address(
                username,
                "456 Oak Avenue",
                "Suite 200",
                "New York",
                "NY",
                "10001",
                "USA",
                true,
                false
        );
        user.addAddress(billingAddress);

        // Add products to cart
        Cart cart = user.getCart();
        cart.addProduct(product1, 1);
        cart.addProduct(product2, 2);
        cart.addProduct(product3, 1);

        return user;
    }

    @Override
    public void saveOrder(Order order) {
        // Implementation for saving order
    }

    @Override
    public void updateUserBalance(String username, double newBalance) {
        // Implementation for updating user balance
    }

    @Override
    public void updateUserPoints(String username, int newPoints) {
        // Implementation for updating user points
    }
}