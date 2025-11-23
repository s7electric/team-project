package use_case.checkout;

public class CheckoutInputData {
    private final String userId;

    public CheckoutInputData(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}