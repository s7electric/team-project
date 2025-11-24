package interface_adapter.homepage;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.manage_address.ManageAddressState;
import interface_adapter.manage_address.ManageAddressViewModel;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.sign_up.SignUpViewModel;
import use_case.homepage.HomepageOutputBoundary;
import use_case.homepage.HomepageOutputData;

public class HomepagePresenter implements HomepageOutputBoundary {
    private final SignUpViewModel signUpViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final HomepageViewModel homepageViewModel;
    private final HomepageState homepageState;
    private final ManageAddressViewModel manageAddressViewModel;
    private final FilterViewModel filterViewModel;
    private final SearchViewModel searchViewModel;

    public HomepagePresenter(SignUpViewModel signUpViewModel,
                             ViewManagerModel  viewManagerModel,
                             LoginViewModel loginViewModel,
                             HomepageViewModel homepageViewModel,
                             HomepageState homepageState,
                             ManageAddressViewModel manageAddressViewModel,
                             FilterViewModel filterViewModel,
                             SearchViewModel searchViewModel) {
        this.signUpViewModel = signUpViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.homepageState = homepageState;
        this.homepageViewModel = homepageViewModel;
        this.manageAddressViewModel = manageAddressViewModel;
        this.filterViewModel = filterViewModel;
        this.searchViewModel = searchViewModel;
    }

    public void switchToSignUpView(){
        this.viewManagerModel.setActiveViewName(this.signUpViewModel.getViewName());
    }
    public void switchToLoginView(){
        this.viewManagerModel.setActiveViewName(this.loginViewModel.getViewName());
    }
    public void switchToCartView(){
        // Not implemented
    }
    public void switchToInfoView(HomepageOutputData homepageOutputData){
        // Not implemented
    }
    public void switchToAddressView(){
        // pass username to manage address state
        ManageAddressState mas = manageAddressViewModel.getState();
        mas.setUsername(homepageState.getUsername());
        manageAddressViewModel.setState(mas);
        this.viewManagerModel.setActiveViewName(this.manageAddressViewModel.getViewName());
    }
    public void switchToFundView(){
        // Not implemented
    }
    public void switchToSearchView(){
        this.viewManagerModel.setActiveViewName(this.searchViewModel.getViewName());
    }
    public void switchToFilterView(){
        this.viewManagerModel.setActiveViewName(this.filterViewModel.getViewName());
    }
    public void switchToListingView(){
        // Not implemented
    }
    public void switchToLogoutView(){
        this.viewManagerModel.setActiveViewName(this.loginViewModel.getViewName());
    }
}
