package interface_adapter.sign_up;

import entity.User;
import use_case.sign_up.SignUpInputBoundary;
import use_case.sign_up.SignUpInputData;
import use_case.sign_up.SignUpOutputBoundary;

/**
 * */
public class SignUpPresenter implements SignUpOutputBoundary{
    private SignUpViewModel signUpViewModel;
    private SignUpState signUpState;

    public SignUpPresenter(){
        this.signUpViewModel = new SignUpViewModel();
        this.signUpState = new SignUpState();
    }

    public void updateSuccess(SignUpOutputData outputData){
        this.signUpState.setSuccess(outputData.getUser());
        this.signUpViewModel.setState(signUpState);
        this.signUpViewModel.firePropertyChange("SignUpSuccess");
    }

    public void updateFailure(SignUpOutputData outputData) {
        this.signUpState.setSuccess(outputData.getError());
        this.signUpViewModel.setFailure(signUpState);
        this.signUpViewModel.firePropertyChange("SignUpFailure");
    }
}
