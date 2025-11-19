package interface_adapter.sign_up;

import interface_adapter.login.LoginViewModel;
import use_case.sign_up.SignUpOutputBoundary;
import interface_adapter.ViewManagerModel;
import use_case.sign_up.SignUpOutputData;

/**
 * This class unwraps the formatted output data from the SignUpInteractor into raw data for the SignUpView.
 * SignUpPresenter contains a SignUpViewModel and a SignUpState.
 * */
public class SignUpPresenter implements SignUpOutputBoundary{
    private SignUpViewModel signUpViewModel;
    private SignUpState signUpState;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;

    /**
     * Creates a SignUpPresenter object to unwrap the formatted output data of SignUpInteractor.
     * */
    public SignUpPresenter(ViewManagerModel viewManagerModel,
                           SignUpViewModel signUpViewModel,
                           LoginViewModel loginViewModel) {
        this.signUpViewModel = new SignUpViewModel();
        this.signUpState = new SignUpState();
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
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
        this.signUpState.setSuccess(outputData.getError());
        this.signUpViewModel.setFailure(signUpState);
        this.signUpViewModel.firePropertyChange("SignUpFailure");
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setActiveViewName(loginViewModel.getViewName());
    }
}
