package interface_adapter.homepage;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.make_listing.*;
import interface_adapter.manage_address.ManageAddressState;
import interface_adapter.manage_address.ManageAddressViewModel;
import interface_adapter.sign_up.SignUpState;
import interface_adapter.sign_up.SignUpViewModel;
import interface_adapter.Product.ProductViewModel;
import interface_adapter.Product.ProductState;
import use_case.homepage.HomepageInputData;
import use_case.homepage.HomepageOutputData;
import view.ManageAddressView;


public class HomepagePresenter {
    private SignUpViewModel signUpViewModel;
    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;
    private HomepageViewModel homepageViewModel;
    private HomepageState homepageState;
    private ProductViewModel productViewModel;
    private ProductState productState;

    public HomepagePresenter(SignUpViewModel signUpViewModel, ViewManagerModel  viewManagerModel, LoginViewModel loginViewModel, HomepageViewModel homepageViewModel, HomepageState homepageState, ProductViewModel productViewModel, ProductState productState) {
        this.signUpViewModel = signUpViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.homepageState = homepageState;
        this.homepageViewModel = homepageViewModel;
        this.productViewModel = productViewModel;
        this.productState = productState;
    }

    public void switchToSignUpView(){
        this.viewManagerModel.setActiveViewName(this.signUpViewModel.getViewName());
    }
    public void switchToLoginView(){
        this.viewManagerModel.setActiveViewName(this.loginViewModel.getViewName());
    }
    public void switchToCartView(){

    }
    public void switchToInfoView(HomepageOutputData homepageOutputData){
        String productId = homepageOutputData.getProductId();
        String username = homepageOutputData.getUsername();
        productState.setProductid(productId);
        productState.setUsername(username);
        productViewModel.setState(productState);
        this.viewManagerModel.setActiveViewName(this.productViewModel.getViewName());
    }
    public void switchToAddressView(){

    }
    public void switchToFundView(){

    }
    public void switchToSearchView(){
        this.viewManagerModel.setActiveViewName(this.searchViewModel.getViewName());
    }
    public void switchToFilterView(){
        this.viewManagerModel.setActiveViewName(this.filterViewModel.getViewName());
    }
    public void switchToListingView(){

    }
    public void switchToLogoutView(){

    }
}
