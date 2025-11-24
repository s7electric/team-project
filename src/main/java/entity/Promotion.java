package entity;

/**
 * Promotion entity representing a discount rule
 * that can be applied to a Cart.
 */
public class Promotion {

    private final String code;
    private final boolean active;
    private final double percentageOff;
    private final double fixedAmountOff;
    private final double minSubtotal;

    public Promotion(String code,
                     boolean active,
                     double percentageOff,
                     double fixedAmountOff,
                     double minSubtotal) {
        this.code = code.toUpperCase();
        this.active = active;
        this.percentageOff = percentageOff;
        this.fixedAmountOff = fixedAmountOff;
        this.minSubtotal = minSubtotal;
    }

    public String getCode() {
        return code;
    }

    public boolean isActive() {
        return active;
    }

    public double getPercentageOff() {
        return percentageOff;
    }

    public double getFixedAmountOff() {
        return fixedAmountOff;
    }

    public double getMinSubtotal() {
        return minSubtotal;
    }

    /**
     * Returns the discount amount for the given subtotal,
     * or 0 if the promotion is not applicable.
     */
    public double calculateDiscount(double subtotal) {
        if (!active) return 0.0;
        if (subtotal < minSubtotal) return 0.0;

        double percentageDiscount = subtotal * percentageOff;
        double discount = percentageDiscount + fixedAmountOff;
        if (discount < 0) discount = 0;
        if (discount > subtotal) discount = subtotal;
        return discount;
    }
}