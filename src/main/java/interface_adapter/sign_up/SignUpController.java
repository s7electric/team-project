package interface_adapter.sign_up;

import use_case.sign_up.SignUpInputBoundary;
import use_case.sign_up.SignUpInputData;

/**
 * This is controller class for the signup use case.
 * SignUpController contains SignUpInteractor
 * */
public class SignUpController {
    private SignUpInputBoundary signUpInteractor;

    /**
     * Creates SignUpController object for the signup use case
     * */
    public SignUpController(){
        this.signUpInteractor = new SignUpInteractor();
    }

    /**
     * Formats the raw data entered by the new user and executes the SignUpInteractor using the SignUpInputData
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     * @param billingAddress the initial billing address of the user
     * */
    public void execute(String username, String password, String email, String billingAddress){
        SignUpInputData inputData = new SignUpInputData(username, password, email, billingAddress);
        signUpInteractor.execute(inputData);
    }
}
