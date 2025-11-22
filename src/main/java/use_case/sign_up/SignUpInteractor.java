package use_case.sign_up;

import entity.Address;
import entity.User;
import interface_adapter.sign_up.SignUpPresenter;

/**
 * This class is the interactor for the signup use case
 * This class contains the SignUpPresenter and the DataAccess
 * */
public class SignUpInteractor implements SignUpInputBoundary{
    private SignUpOutputBoundary signUpPresenter;
    private SignUpDataAccessInterface dataAccess;

    /**
     * Creates the SignUpInteractor object for the signup use case
     * @param signUpPresenter the presenter for the signup use case
     * @param dataAccess the data access object
     * */
    public SignUpInteractor(SignUpOutputBoundary signUpPresenter, SignUpDataAccessInterface dataAccess){
        this.signUpPresenter = signUpPresenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Executes the interactor for the signup use case using the formatted input data
     * @param inputData the formatted input data needed for the execution of the interactor
     * */
    public void execute(SignUpInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        String email = inputData.getEmail();
        String billingAddress = inputData.getBillingAddress();

        // Checks if a user with the same username exists
        boolean oldUser = dataAccess.checkUserExists(username);

        // if a user with same username exists, informs the new user about it
        if (oldUser) {
            SignUpOutputData outputData = new SignUpOutputData("An user with the same username exists, try to login!");
            signUpPresenter.updateFailure(outputData);

        // if not try to sign up the new user
        } else {
            try {
                PasswordStrengthChecker.checkStrength(username, email, password);
                User newUser = new User(username, password, email, billingAddress);
                dataAccess.createUser(newUser);
                SignUpOutputData outputData = new SignUpOutputData(newUser);
                signUpPresenter.updateSuccess(outputData);

            // if the signing up process failed, informs the new user about it
            } catch (IllegalArgumentException e) {
                SignUpOutputData outputData = new SignUpOutputData(e.getMessage());
                signUpPresenter.updateFailure(outputData);
            }
        }
    }

    /**
     * Switches to log in view
     * */
    public void switchToLoginView(){
        this.signUpPresenter.switchToLoginView();
    }

    /**
     * Switches to homepage view
     * */
    public void switchToHomepageView(){
        this.signUpPresenter.switchToHomepageView();
    }
}
