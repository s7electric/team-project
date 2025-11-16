package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * Presenter for the login use case. Updates the LoginViewModel and view navigation state.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final String loggedInViewName;

    public LoginPresenter(LoginViewModel loginViewModel,
                          ViewManagerModel viewManagerModel,
                          String loggedInViewName) {
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewName = loggedInViewName;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        LoginState newState = new LoginState(outputData.getUsername(), null, true);
        loginViewModel.setState(newState);
        viewManagerModel.setActiveViewName(loggedInViewName);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        LoginState currentState = loginViewModel.getState();
        LoginState newState = new LoginState(currentState.getUsername(), errorMessage, false);
        loginViewModel.setState(newState);
    }
}
