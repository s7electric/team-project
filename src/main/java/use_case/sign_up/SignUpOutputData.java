package use_case.sign_up;

import entity.User;

/**
 * This class contains the formatted output data for the execution of the SignUpPresenter
 * SignUpOutputData contains a user or an error.
 * */
public class SignUpOutputData {
    private User user;
    private String error;

    /**
     * Creates an SignUpOutputData object if the signing up was successful
     * @param user the user currently signed up
     * */
    public SignUpOutputData(User user){
        this.user = user;
    }

    /**
     * Creates an SignUpOutputData object if the signing up failed
     * @param error the error message of the failure
     * */
    public SignUpOutputData(String error){
        this.error = error;
    }

    public User getUser(){
        return user;
    }

    public String getError(){
        return error;
    }
}
