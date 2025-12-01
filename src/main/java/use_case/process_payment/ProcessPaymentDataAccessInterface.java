package use_case.process_payment;

import entity.User;

public interface ProcessPaymentDataAccessInterface {
    User getUser(String username);
    void saveUser(User user);
}