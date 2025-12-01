package use_case.process_payment;

public interface ProcessPaymentInputBoundary {
    void execute(ProcessPaymentInputData inputData);
}