package interface_adapter.process_payment;

import use_case.process_payment.ProcessPaymentInputBoundary;
import use_case.process_payment.ProcessPaymentInputData;

public class ProcessPaymentController {
    private final ProcessPaymentInputBoundary interactor;

    public ProcessPaymentController(ProcessPaymentInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, double amountToPay, int pointsToUse) {
        ProcessPaymentInputData inputData = new ProcessPaymentInputData(username, amountToPay, pointsToUse);
        interactor.execute(inputData);
    }
}