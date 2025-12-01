package use_case.homepage;

public class HomepageInteractor implements HomepageInputBoundary {
    private HomepageOutputBoundary homepagePresenter;
    private AddFundsDataAccessInterface addFundsDataAccess;
    public HomepageInteractor(HomepageOutputBoundary homepagePresenter, 
        AddFundsDataAccessInterface addFundsDataAccess) {
        this.homepagePresenter = homepagePresenter;
        this.addFundsDataAccess = addFundsDataAccess;
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
        HomepageOutputData homepageOutputData = new HomepageOutputData(homepageInputData.getProductId(), homepageInputData.getUsername());
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
    
    public void addFunds(String username, double amount){
        addFundsDataAccess.addFunds(username, amount);
    }
}
