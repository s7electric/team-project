package use_case.checkout;

public interface CheckoutInputBoundary {
    //Initiates the checkout use case.
    void execute(CheckoutInputData inputData);
}
