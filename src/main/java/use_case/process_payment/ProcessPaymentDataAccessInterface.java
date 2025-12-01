package use_case.process_payment;

import entity.User;

public interface ProcessPaymentDataAccessInterface {
    User getUser(String username);
    void updateUserBalance(String username, double newBalance);
    void updateUserPoints(String username, int newPoints);
}
