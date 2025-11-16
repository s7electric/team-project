package use_case.sign_up;

import entity.User;

/**
 * This interface is for the SignUpPresenter to implement and for the SignUpInteractor to call its methods
 * */
public interface SignUpOutputBoundary {
    /**
     * Updates the view to show the use case was successful
     * @param user the user currently signed up
     * */
    public void updateSuccess(User user);

    /**
     * Updates the view to show the use case was successful
     * @param error the error regarding the failure
     * */
    public void updateFailure(String error);
}
