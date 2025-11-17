package interface_adapter.sign_up;

import use_case.sign_up.SignUpOutputBoundary;
import use_case.sign_up.SignUpOutputData;

/**
 * This class unwraps the formatted output data from the SignUpInteractor into raw data for the SignUpView.
 * SignUpPresenter contains a SignUpViewModel and a SignUpState.
 * */
public class SignUpPresenter implements SignUpOutputBoundary{
    private SignUpViewModel signUpViewModel;
    private SignUpState signUpState;

    /**
     * Creates a SignUpPresenter object to unwrap the formatted output data of SignUpInteractor.
     * */
    public SignUpPresenter(){
        this.signUpViewModel = new SignUpViewModel();
        this.signUpState = new SignUpState();
    }

    /**
     * Updates the SignUpView to show that the process was successful.
     * @param outputData the output data of the SignUpInteractor
     * */
    public void updateSuccess(SignUpOutputData outputData){
        this.signUpState.setSuccess(outputData.getUser());
        this.signUpViewModel.setState(signUpState);
        this.signUpViewModel.firePropertyChange("SignUpSuccess");
    }

    /**
     * Updates the SignUpView to show that the process has failed.
     * @param outputData the output data of the SignUpInteractor
     * */
    public void updateFailure(SignUpOutputData outputData) {
        this.signUpState.setFailure(outputData.getError());
        this.signUpViewModel.setState(signUpState);
        this.signUpViewModel.firePropertyChange("SignUpFailure");
    }
}
