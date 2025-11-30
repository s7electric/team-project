package interface_adapter.logout;

import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutState;
import interface_adapter.logout.LogoutViewModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

/**
 * The Presenter for the Logout Use Case.
 */
public class LogoutPresenter implements LogoutOutputBoundary {

    private final HomepageViewModel homepageViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final LogoutViewModel logoutViewModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel,
                           HomepageViewModel homepageViewModel,
                           LoginViewModel loginViewModel,
                           LogoutViewModel logoutViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homepageViewModel = homepageViewModel;
        this.loginViewModel = loginViewModel;
        this.logoutViewModel = logoutViewModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData response) {
        HomepageState current = this.homepageViewModel.getState();
        HomepageState next = new HomepageState(null);
        if (current != null) {
            next.setSearchText(current.getSearchText());
            next.setProducts(current.getProducts());
        }
        homepageViewModel.setState(next);

        logoutViewModel.setState(new LogoutState());
        loginViewModel.setState(new LoginState());
        viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
    }

    @Override
    public void switchToHomePage() {
        viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
    }
}
