package interface_adapter.login;

import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.sign_up.SignUpViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * Presenter for the login use case. Updates view models and triggers navigation.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final HomepageViewModel homepageViewModel;
    private final LoginViewModel loginViewModel;
    private final SignUpViewModel signUpViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          HomepageViewModel homepageViewModel,
                          LoginViewModel loginViewModel,
                          SignUpViewModel signUpViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homepageViewModel = homepageViewModel;
        this.loginViewModel = loginViewModel;
        this.signUpViewModel = signUpViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        HomepageState current = homepageViewModel.getState();
        HomepageState next = new HomepageState(outputData.getUsername());
        if (current != null) {
            next.setSearchText(current.getSearchText());
            next.setProducts(current.getProducts());
        }
        homepageViewModel.setState(next);

        loginViewModel.setState(new LoginState());
        viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        LoginState previous = loginViewModel.getState();
        LoginState next = new LoginState(
                previous != null ? previous.getUsername() : "",
                errorMessage,
                previous != null && previous.isLoggedIn());
        if (previous != null) {
            next.setPassword(previous.getPassword());
        }
        loginViewModel.setState(next);
    }

    @Override
    public void switchToSignUpView() {
        viewManagerModel.setActiveViewName(signUpViewModel.getViewName());
    }

    @Override
    public void switchToHomePage() {
        viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
    }
}
