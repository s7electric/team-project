package interface_adapter.sign_up;

import entity.User;

/**
 * This class contains the data needed for state of the signup use case.
 * SignUpState contains a user or an error.
 * */
public class SignUpState {
    private User user;
    private String error;

    public void setSuccess(User user){
        this.user = user;
    }

    public void setFailure(String error){
        this.error = error;
    }

    public User getSuccess(){
        return user;
    }

    public String getFailure(){
        return error;
    }
}
