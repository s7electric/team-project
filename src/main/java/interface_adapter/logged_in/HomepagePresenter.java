package interface_adapter.logged_in;

import use_case.logged_in.LoggedInOutputBoundary;
import interface_adapter.ViewManagerModel;
import interface_adapter.make_listing.MakeListingViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.add_to_cart.AddToCartView;

/**
 * The presenter for the logged-in use case.
 */
public class LoggedInPresenter implements LoggedInOutputBoundary {

    private final ViewManagerModel viewManagerModel;

    public LoggedInPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void switchToMakeListingView() {
        viewManagerModel.setState(new MakeListingViewModel());
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(new LoginViewModel());
    }

    @Override
    public void switchToAddToCartView() {
        viewManagerModel.setState(new AddToCartView());
    }

    // Implement other view switching methods as needed
}
