package use_case.checkout;

/**
 * Input data for the checkout use case
 * Contains all the data needed to process a checkout
 */
public class CheckoutInputData {
    private final String username;  // Changed from userId to username

    public CheckoutInputData(String username) {
        this.username = username;
    }

    // Getter
    public String getUsername() { return username; }  // Changed from getUserId
}