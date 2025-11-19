package use_case.login;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface LoginInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */

    void login(LoginInputData loginInputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToSignUpView();

    /**
     * Executes the switch to home page view use case.
     */
    void switchToHomePage();
}

