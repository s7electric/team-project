package use_case.process_payment;

import entity.User;

public class ProcessPaymentInteractor implements ProcessPaymentInputBoundary {
    private final ProcessPaymentDataAccessInterface dataAccess;
    private final ProcessPaymentOutputBoundary outputBoundary;

    public ProcessPaymentInteractor(ProcessPaymentDataAccessInterface dataAccess,
                                    ProcessPaymentOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(ProcessPaymentInputData inputData) {
        try {
            // 1. Get current user
            User user = dataAccess.getUser(inputData.getUsername());

            // 2. Validate sufficient funds
            double currentBalance = user.getBalance();
            double amountToPay = inputData.getAmountToPay();

            if (currentBalance < amountToPay) {
                outputBoundary.presentPaymentFailure("Insufficient balance");
                return;
            }

            // 3. Validate sufficient points
            int currentPoints = user.getPointsBalance();
            int pointsToUse = inputData.getPointsToUse();

            if (currentPoints < pointsToUse) {
                outputBoundary.presentPaymentFailure("Insufficient points");
                return;
            }

            // 4. Calculate points earned (1 point per $10 spent)
            int pointsEarned = (int) (amountToPay / 10);

            // 5. Update user balance and points
            user.removeBalance(amountToPay);
            user.removePointsBalance(pointsToUse);
            user.addPointsBalance(pointsEarned);

            // 6. Clear user's cart after payment
            user.getCart().getProducts().clear();

            // 7. Save updated user
            dataAccess.updateUserBalance(inputData.getUsername(), user.getBalance());
            dataAccess.updateUserPoints(inputData.getUsername(), user.getPointsBalance());

            // 8. Prepare success output
            ProcessPaymentOutputData outputData = new ProcessPaymentOutputData(
                    inputData.getUsername(),
                    amountToPay,
                    pointsToUse,
                    pointsEarned,
                    user.getBalance(),
                    user.getPointsBalance()
            );

            outputBoundary.presentPaymentSuccess(outputData);

        } catch (Exception e) {
            outputBoundary.presentPaymentFailure("Payment processing failed: " + e.getMessage());
        }
    }
}
