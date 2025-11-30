package interface_adapter.add_to_cart;

import interface_adapter.ViewManagerModel;
import use_case.add_to_cart.AddToCartOutputBoundary;
import use_case.add_to_cart.AddToCartOutputData;

public class AddToCartPresenter implements AddToCartOutputBoundary {

    private final AddToCartViewModel addToCartViewModel;
    private final ViewManagerModel viewManagerModel;

    public AddToCartPresenter(ViewManagerModel viewManagerModel,
                              AddToCartViewModel addToCartViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.addToCartViewModel = addToCartViewModel;
    }

    @Override
    public void prepareSuccessView(AddToCartOutputData response){
        // On success
        final AddToCartState addToCartState = addToCartViewModel.getState();
        addToCartState.setMessage("Added " + response.getProductName() + " to cart!");
        addToCartViewModel.setState(addToCartState);
        addToCartViewModel.firePropertyChange();
    }
    @Override
    public void prepareFailureView(String message) {
        final AddToCartState addToCartState = addToCartViewModel.getState();
        addToCartState.setQuantityError(message);
        addToCartViewModel.setState(addToCartState);
        addToCartViewModel.firePropertyChange();
    }
}
