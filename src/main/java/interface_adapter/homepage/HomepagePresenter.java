package interface_adapter.homepage;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
// import interface_adapter.make_listing.*;
import interface_adapter.manage_address.ManageAddressState;
import interface_adapter.manage_address.ManageAddressViewModel;
import interface_adapter.sign_up.SignUpState;
import interface_adapter.sign_up.SignUpViewModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.Product.ProductViewModel;
import interface_adapter.Product.ProductState;
import interface_adapter.logout.LogoutState;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.make_listing.MakeListingViewModel;
import use_case.homepage.HomepageInputData;
import use_case.homepage.HomepageOutputBoundary;
import use_case.homepage.HomepageOutputData;
import view.ManageAddressView;


public class HomepagePresenter implements HomepageOutputBoundary {
    private SignUpViewModel signUpViewModel;
    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;
    private HomepageViewModel homepageViewModel;
    private HomepageState homepageState;
    private ProductViewModel productViewModel;
    private ProductState productState;
    private SearchViewModel searchViewModel;
    private FilterViewModel filterViewModel;
    private LogoutViewModel logoutViewModel;
    private MakeListingViewModel makeListingViewModel;
    private Runnable openManageAddress;
    private Runnable openCart;

    public HomepagePresenter(SignUpViewModel signUpViewModel,
                             ViewManagerModel  viewManagerModel,
                             LoginViewModel loginViewModel,
                             HomepageViewModel homepageViewModel,
                             HomepageState homepageState,
                             ProductViewModel productViewModel,
                             ProductState productState,
                             SearchViewModel searchViewModel,
                             FilterViewModel filterViewModel,
                             LogoutViewModel logoutViewModel,
                             MakeListingViewModel makeListingViewModel,
                             Runnable openManageAddress,
                             Runnable openCart) {
        this.signUpViewModel = signUpViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.homepageState = homepageState;
        this.homepageViewModel = homepageViewModel;
        this.productViewModel = productViewModel;
        this.productState = productState;
        this.searchViewModel = searchViewModel;
        this.filterViewModel = filterViewModel;
        this.logoutViewModel = logoutViewModel;
        this.makeListingViewModel = makeListingViewModel;
        this.openManageAddress = openManageAddress;
        this.openCart = openCart;
    }

    @Override
    public void switchToSignUpView(){
        this.viewManagerModel.setActiveViewName(this.signUpViewModel.getViewName());
    }
    @Override
    public void switchToLoginView(){
        this.viewManagerModel.setActiveViewName(this.loginViewModel.getViewName());
    }
    @Override
    public void switchToCartView(){
        if (openCart != null) {
            openCart.run();
        }
    }
    @Override
    public void switchToInfoView(HomepageOutputData homepageOutputData){
        String productId = homepageOutputData.getProductId();
        String username = homepageOutputData.getUsername();
        productState.setProductid(productId);
        productState.setUsername(username);
        productViewModel.setState(productState);
        this.viewManagerModel.setActiveViewName(this.productViewModel.getViewName());
    }
    @Override
    public void switchToAddressView(){
        if (openManageAddress != null) {
            openManageAddress.run();
        }
    }
    @Override
    public void switchToFundView(){

    }
    @Override
    public void switchToSearchView(){
        this.viewManagerModel.setActiveViewName(this.searchViewModel.getViewName());
    }
    @Override
    public void switchToFilterView(){
        this.viewManagerModel.setActiveViewName(this.filterViewModel.getViewName());
    }
    @Override
    public void switchToListingView(){
        this.viewManagerModel.setActiveViewName(this.makeListingViewModel.getViewName());
    }
    @Override
    public void switchToLogoutView(){
        HomepageState current = homepageViewModel.getState();
        String username = current.getUsername();
        LogoutState next = new LogoutState();
        next.setUsername(username);
        logoutViewModel.setState(next);
        this.viewManagerModel.setActiveViewName(this.logoutViewModel.getViewName());
    }
}
