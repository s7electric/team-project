package use_case.apply_promotion;

/**
 * Input data for the Apply Promotion use case.
 */
public class ApplyPromotionInputData {
    private final String username;
    private final String promoCode;

    public ApplyPromotionInputData(String username, String promoCode) {
        this.username = username;
        this.promoCode = promoCode;
    }

    public String getUsername() {
        return username;
    }

    public String getPromoCode() {
        return promoCode;
    }
}