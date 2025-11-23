package use_case.checkout;

import entity.Order;
import entity.User;

public interface CheckoutDataAccessInterface {
    User getUser(String userId);
    void saveOrder(Order order); // If you have an Order entity
}

