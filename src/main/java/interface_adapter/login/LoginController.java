package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

public class LoginController {

    private final LoginInputBoundary loginUseCaseInteractor;

    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    public void login(String username, String password){
        LoginInputData input = new LoginInputData(username, password);
        loginUseCaseInteractor.login(input);
    }

    /**
     * Executes the "switch to Sign Up View" Use Case.
     */
    public void switchToSignUpView() {
        loginUseCaseInteractor.switchToSignUpView();
    }

    /**
     * Executes the "switch to Home Page" Use Case.
     */
    public void switchToHomePage() {
        loginUseCaseInteractor.switchToHomePage();
    }
}
