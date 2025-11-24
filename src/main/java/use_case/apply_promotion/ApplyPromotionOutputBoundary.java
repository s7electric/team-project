package use_case.apply_promotion;

/**
 * Output boundary for the Apply Promotion use case.
 */
public interface ApplyPromotionOutputBoundary {
    void prepareSuccessView(ApplyPromotionOutputData outputData);
    void prepareFailView(String message);
}