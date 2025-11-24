package use_case.homepage;

public interface HomepageOutputBoundary {
    void switchToSignUpView();
    void switchToLoginView();
    void switchToCartView();
    void switchToInfoView(HomepageOutputData homepageOutputData);
    void switchToAddressView();
    void switchToFundView();
    void switchToSearchView();
    void switchToFilterView();
    void switchToListingView();
    void switchToLogoutView();
}
