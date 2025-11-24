package use_case.homepage;

public interface HomepageInputBoundary {
    void switchToSignUpView();
    void switchToLoginView();
    void switchToCartView();
    void switchToInfoView(HomepageInputData homepageInputData);
    void switchToAddressView();
    void switchToFundView();
    void switchToSearchView();
    void switchToFilterView();
    void switchToListingView();
    void switchToLogoutView();
}
