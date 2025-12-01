package use_case.process_payment;

public class ProcessPaymentInputData {
    private final String username;
    private final double amountToPay;
    private final int pointsToUse;

    public ProcessPaymentInputData(String username, double amountToPay, int pointsToUse) {
        this.username = username;
        this.amountToPay = amountToPay;
        this.pointsToUse = pointsToUse;
    }

    public String getUsername() { return username; }
    public double getAmountToPay() { return amountToPay; }
    public int getPointsToUse() { return pointsToUse; }
}