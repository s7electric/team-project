package use_case.homepage;

public class HomepageInteractor implements HomepageInputBoundary {
    private HomepageOutputBoundary homepagePresenter;
    public HomepageInteractor(HomepageOutputBoundary homepagePresenter) {
        this.homepagePresenter = homepagePresenter;
    }
    public void switchToSignUpView(){
        this.homepagePresenter.switchToSignUpView();
    }
    public void switchToLoginView(){
        this.homepagePresenter.switchToLoginView();
    }
    public void switchToCartView(){
        this.homepagePresenter.switchToCartView();
    }
    public void switchToInfoView(HomepageInputData homepageInputData){
        HomepageOutputData homepageOutputData = new HomepageOutputData(homepageInputData.getProductId());
        this.homepagePresenter.switchToInfoView(homepageOutputData);
    }
    public void switchToAddressView(){
        this.homepagePresenter.switchToAddressView();
    }
    public void switchToFundView(){
        this.homepagePresenter.switchToFundView();
    }
    public void switchToSearchView(){
        this.homepagePresenter.switchToSearchView();
    }
    public void switchToFilterView(){
        this.homepagePresenter.switchToFilterView();
    }
    public void switchToListingView(){
        this.homepagePresenter.switchToListingView();
    }
    public void switchToLogoutView(){
        this.homepagePresenter.switchToLogoutView();
    }
}
