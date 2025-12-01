package use_case.process_payment;

public class ProcessPaymentOutputData {
    private final String username;
    private final double amountPaid;
    private final int pointsUsed;
    private final int pointsEarned;
    private final double newBalance;
    private final int newPoints;

    public ProcessPaymentOutputData(String username, double amountPaid, int pointsUsed,
                                    int pointsEarned, double newBalance, int newPoints) {
        this.username = username;
        this.amountPaid = amountPaid;
        this.pointsUsed = pointsUsed;
        this.pointsEarned = pointsEarned;
        this.newBalance = newBalance;
        this.newPoints = newPoints;
    }

    // Getters
    public String getUsername() { return username; }
    public double getAmountPaid() { return amountPaid; }
    public int getPointsUsed() { return pointsUsed; }
    public int getPointsEarned() { return pointsEarned; }
    public double getNewBalance() { return newBalance; }
    public int getNewPoints() { return newPoints; }
}