package interface_adapter.sign_up;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.search.SearchState;
import use_case.sign_up.SignUpOutputBoundary;
import use_case.sign_up.SignUpOutputData;
import interface_adapter.homepage.*;

/**
 * This class unwraps the formatted output data from the SignUpInteractor into raw data for the SignUpView.
 * SignUpPresenter contains a SignUpViewModel and a SignUpState, ViewManagerModel, LoginViewModel, HomePageViewModel, HomePageState.
 * */
public class SignUpPresenter implements SignUpOutputBoundary{
    private SignUpViewModel signUpViewModel;
    private SignUpState signUpState;
    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;
    private HomepageViewModel homepageViewModel;
    private HomepageState homepageState;

    /**
     * Creates a SignUpPresenter object to unwrap the formatted output data of SignUpInteractor.
     * @param signUpViewModel the view model for signup use case
     * @param signUpState the state for the signup use case
     * @param viewManagerModel the view manager model for the app
     * @param loginViewModel the view model for the log in view
     * @param homepageViewModel the view model for the homepage view
     * @param homepageState the state for the homepage view
     * */
    public SignUpPresenter(SignUpViewModel signUpViewModel, SignUpState signUpState, ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, HomepageViewModel homepageViewModel, HomepageState homepageState){
        this.signUpViewModel = signUpViewModel;
        this.signUpState = signUpState;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.homepageViewModel = homepageViewModel;
        this.homepageState = homepageState;
    }

    /**
     * Updates the SignUpView to show that the process was successful.
     * @param outputData the output data of the SignUpInteractor
     * */
    public void updateSuccess(SignUpOutputData outputData){
        HomepageState homepageState = homepageViewModel.getState();
        String username = homepageState.getUsername();
        HomepageState homepageStateNew = new HomepageState(username);
        homepageStateNew.setUsername(outputData.getUser().getUsername());
        homepageStateNew.setSearchText(homepageState.getSearchText());
        homepageStateNew.setProducts(homepageState.getProducts());
        this.homepageViewModel.setState(homepageStateNew);
        this.viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
    }

    /**
     * Updates the SignUpView to show that the process has failed.
     * @param outputData the output data of the SignUpInteractor
     * */
    public void updateFailure(SignUpOutputData outputData) {
        SignUpState signUpStateNew = new SignUpState();
        signUpStateNew.setSuccess(null);
        signUpStateNew.setFailure(outputData.getError());
        this.signUpViewModel.setState(signUpStateNew);
    }

    /**
     * Switches to log in view
     * */
    public void switchToLoginView(){
        this.viewManagerModel.setActiveViewName(loginViewModel.getViewName());
    }

    /**
     * Switches to homepage view and passes the username
     * */
    public void switchToHomepageView(){
        HomepageState homepageState = homepageViewModel.getState();
        String username = homepageState.getUsername();
        HomepageState homepageStateNew = new HomepageState(username);
        homepageStateNew.setSearchText(homepageState.getSearchText());
        homepageStateNew.setProducts(homepageState.getProducts());
        this.homepageViewModel.setState(homepageStateNew);
        this.viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
    }
}
