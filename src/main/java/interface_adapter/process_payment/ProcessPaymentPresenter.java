package interface_adapter.process_payment;

import use_case.process_payment.ProcessPaymentOutputBoundary;
import use_case.process_payment.ProcessPaymentOutputData;

public class ProcessPaymentPresenter implements ProcessPaymentOutputBoundary {
    private ProcessPaymentView view;

    public void setView(ProcessPaymentView view) {
        this.view = view;
    }

    @Override
    public void presentPaymentSuccess(ProcessPaymentOutputData outputData) {
        String message = String.format(
                "Payment successful!\n\n" +
                        "Amount paid: $%.2f\n" +
                        "Points used: %d\n" +
                        "Points earned: %d\n" +
                        "New balance: $%.2f\n" +
                        "New points: %d",
                outputData.getAmountPaid(),
                outputData.getPointsUsed(),
                outputData.getPointsEarned(),
                outputData.getNewBalance(),
                outputData.getNewPoints()
        );

        if (view != null) {
            view.showPaymentResult(true, message);
        }
    }

    @Override
    public void presentPaymentFailure(String errorMessage) {
        if (view != null) {
            view.showPaymentResult(false, errorMessage);
        }
    }
}