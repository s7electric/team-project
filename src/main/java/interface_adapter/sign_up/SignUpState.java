package interface_adapter.sign_up;

import entity.User;

/**
 * This class contains the data needed for state of the signup use case.
 * SignUpState contains a username or an error.
 * */
public class SignUpState {
    private String username;
    private String error;

    public void setSuccess(String username){
        this.username = username;
    }

    public void setFailure(String error){
        this.error = error;
    }

    public String getSuccess(){
        return username;
    }

    public String getFailure(){
        return error;
    }
}
