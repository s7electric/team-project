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
    public void updateSuccess(SignUpOutputData signUpOutputData);

    /**
     * Updates the view to show the use case was successful
     * @param signUpOutputData the formatted data for a failed signup process
     * */
    public void updateFailure(SignUpOutputData signUpOutputData);
}
