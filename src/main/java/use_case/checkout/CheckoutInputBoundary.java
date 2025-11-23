package use_case.checkout;

public interface CheckoutInputBoundary {
    //Initiates the checkout use case.
    public void execute(CheckoutInputData inputData);
}
