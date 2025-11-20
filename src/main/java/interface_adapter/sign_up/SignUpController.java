package interface_adapter.sign_up;

import use_case.sign_up.SignUpInputBoundary;
import use_case.sign_up.SignUpInputData;
import use_case.sign_up.SignUpInteractor;

/**
 * This is controller class for the signup use case.
 * SignUpController contains SignUpInteractor.
 * */
public class SignUpController {
    private final SignUpInputBoundary signUpInteractor;

    /**
     * Creates SignUpController object for the signup use case
     * @param signUpInteractor the interactor fo the signup use case
     * */
    public SignUpController(SignUpInputBoundary signUpInteractor){
        this.signUpInteractor = signUpInteractor;
    }

    /**
     * Formats the raw data entered by the new user and executes the SignUpInteractor using the SignUpInputData
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     * @param billingAddress the initial billing address of the user
     * */
    public void signUp (String username, String password, String email, String billingAddress){
        SignUpInputData inputData = new SignUpInputData(username, password, email, billingAddress);
        this.signUpInteractor.execute(inputData);
    }

    /**
     * Switches to log in view
     * */
    public void switchToLoginView(){
        this.signUpInteractor.switchToLoginView();
    }

    /**
     * Switches to logged out view
     * */
    public void switchToLoggedOutView(){
        this.signUpInteractor.switchToLoggedOutView();
    }

    /**
     * Switches to logged in view
     * */
    public void switchToLoggedInView(){
        this.signUpInteractor.switchToLoggedInView();
    }

    public void switchToLoginView(){
        signUpInteractor.switchToLoginView();
    }
}
