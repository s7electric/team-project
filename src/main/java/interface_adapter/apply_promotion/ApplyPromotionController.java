package interface_adapter.apply_promotion;

import use_case.apply_promotion.ApplyPromotionInputBoundary;
import use_case.apply_promotion.ApplyPromotionInputData;

/**
 * Controller for the Apply Promotion use case.
 * Takes raw UI input and constructs input data for the interactor.
 */
public class ApplyPromotionController {

    private final ApplyPromotionInputBoundary interactor;

    public ApplyPromotionController(ApplyPromotionInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void applyPromotion(String username, String promoCode) {
        ApplyPromotionInputData inputData = new ApplyPromotionInputData(username, promoCode);
        interactor.execute(inputData);
    }
}