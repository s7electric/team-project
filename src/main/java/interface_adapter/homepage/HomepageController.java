package interface_adapter.homepage;

import use_case.homepage.*;

public class HomepageController {
    private HomepageInputBoundary homepageInteractor;
    public HomepageController(HomepageInputBoundary homepageInteractor) {
        this.homepageInteractor =  homepageInteractor;
    }
    public void switchToSignUpView(){
        this.homepageInteractor.switchToSignUpView();
    }
    public void switchToLoginView(){
        this.homepageInteractor.switchToLoginView();
    }
    public void switchToCartView(){
        this.homepageInteractor.switchToCartView();
    }
    public void switchToInfoView(String productId, String username){
        HomepageInputData homepageInputData = new HomepageInputData(productId, username);
        this.homepageInteractor.switchToInfoView(homepageInputData);
    }
    public void switchToAddressView(){
        this.homepageInteractor.switchToAddressView();
    }
    public void switchToFundView(){
        this.homepageInteractor.switchToFundView();
    }
    public void switchToSearchView(){
        this.homepageInteractor.switchToSearchView();
    }
    public void switchToFilterView(){
        this.homepageInteractor.switchToFilterView();
    }
    public void switchToListingView(){
        this.homepageInteractor.switchToListingView();
    }
    public void switchToLogoutView(){
        this.homepageInteractor.switchToLogoutView();
    }
}
