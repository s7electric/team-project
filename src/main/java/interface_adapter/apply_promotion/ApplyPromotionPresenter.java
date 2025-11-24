package interface_adapter.apply_promotion;

import use_case.apply_promotion.ApplyPromotionOutputBoundary;
import use_case.apply_promotion.ApplyPromotionOutputData;

/**
 * Presenter converts use case output data into state for the ViewModel.
 */
public class ApplyPromotionPresenter implements ApplyPromotionOutputBoundary {

    private final ApplyPromotionViewModel viewModel;

    public ApplyPromotionPresenter(ApplyPromotionViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ApplyPromotionOutputData outputData) {
        ApplyPromotionState state = viewModel.getState();
        state.setUsername(outputData.getUsername());
        state.setPromoCode(outputData.getPromoCode());
        state.setItems(outputData.getItems());
        state.setSubtotal(outputData.getSubtotal());
        state.setDiscount(outputData.getDiscount());
        state.setTotal(outputData.getTotal());
        state.setMessage(outputData.getMessage());
        viewModel.setState(state);
    }

    @Override
    public void prepareFailView(String message) {
        ApplyPromotionState state = viewModel.getState();
        state.setMessage(message);
        viewModel.setState(state);
    }
}