package use_case.checkout;

import entity.Order;
import entity.User;

/**
 * Data Access Interface for checkout operations
 * This abstracts the data layer from the business logic
 */
public interface CheckoutDataAccessInterface {
    User getUser(String username);  // Changed parameter from userId to username

    void saveOrder(Order order);

    void updateUserBalance(String username, double newBalance);
    void updateUserPoints(String username, int newPoints);
}