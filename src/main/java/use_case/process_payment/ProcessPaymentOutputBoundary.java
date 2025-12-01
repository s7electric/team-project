package use_case.process_payment;

public interface ProcessPaymentOutputBoundary {
    void presentPaymentSuccess(ProcessPaymentOutputData outputData);
    void presentPaymentFailure(String errorMessage);
}