package use_case.sign_up;

import data_access.DataAccessInterface;
import entity.User;

/**
 * This class is the interactor for the signup use case
 * This class contains the SignUpPresenter and the DataAccess
 * */
public class SignUpInteractor implements SignUpInputBoundary{
    private SignUpOutputBoundary signUpPresenter;
    private DataAccessInterface dataAccess;

    /**
     * Creates the SignUpInteractor object for the signup use case
     * */
    public SignUpInteractor(){
        this.signUpPresenter = new SignUpPresenter();
        this.dataAccess = new DataAccess();
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
        if (!oldUser) {
            SignUpOutputData outputData = new SignUpOutputData("An user with the same username exists, try to login!");
            signUpPresenter.updateFailure(outputData);

        // if not try to sign up the new user
        } else {
            try {
                User newUser = new User(username, password, email, billingAddress);
                dataAccess.createUser(newUser);
                SignUpOutputData outputData = new SignUpOutputData(newUser);
                signUpPresenter.updateSuccess(outputData);

            // if the signing up process failed, informs the new user about it
            } catch (IllegalArgumentException e) {
                SignUpOutputData outputData = new SignUpOutputData(e.getMessage());
                signUpPresenter.upadteFailure(outputData);
            }
        }
    }
}
