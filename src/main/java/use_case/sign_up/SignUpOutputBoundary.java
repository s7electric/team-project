package use_case.sign_up;

import entity.User;

/**
 * This interface is for the SignUpPresenter to implement and for the SignUpInteractor to call its methods
 * */
public interface SignUpOutputBoundary {
    /**
     * Updates the view to show the use case was successful
     * @param signUpOutputData the formatted data for a successful signup process
     * */
    void updateSuccess(SignUpOutputData signUpOutputData);

    /**
     * Updates the view to show the use case was successful
     * @param signUpOutputData the formatted data for a failed signup process
     * */
    void updateFailure(SignUpOutputData signUpOutputData);

    /**
     * Switches to log in view
     * */
    void switchToLoginView();

    /**
     * Switches to logged out view
     * */
    void switchToLoggedOutView();

    /**
     * Switches to logged in view
     * */
    void switchToLoggedInView();
}
