package interface_adapter.login;

import interface_adapter.HomePagLoggedIN.HomePageLoggedInState;
import interface_adapter.HomePagLoggedIN.HomePageLoggedInViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.sign_up.SignUpViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * Presenter for the login use case. Updates view models and triggers navigation.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final HomePageLoggedInViewModel homePageLoggedInViewModel;
    private final LoginViewModel loginViewModel;
    private final SignUpViewModel signUpViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          HomePageLoggedInViewModel homePageLoggedInViewModel,
                          LoginViewModel loginViewModel,
                          SignUpViewModel signUpViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homePageLoggedInViewModel = homePageLoggedInViewModel;
        this.loginViewModel = loginViewModel;
        this.signUpViewModel = signUpViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        HomePageLoggedInState homePageLoggedInState = homePageLoggedInViewModel.getState();
        homePageLoggedInState.setUsername(outputData.getUsername());
        homePageLoggedInViewModel.setState(homePageLoggedInState);

        loginViewModel.setState(new LoginState());
        viewManagerModel.setActiveViewName(homePageLoggedInViewModel.getViewName());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        LoginState state = loginViewModel.getState();
        state.setErrorMessage(errorMessage);
        loginViewModel.setState(state);
    }

    @Override
    public void switchToSignUpView() {
        viewManagerModel.setActiveViewName(signUpViewModel.getViewName());
    }

    @Override
    public void switchToHomePage() {
        viewManagerModel.setActiveViewName(homeViewModel.getViewName());
    }
}
