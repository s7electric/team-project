package interface_adapter.make_listing;

import use_case.make_listing.MakeListingInputBoundary;
import use_case.make_listing.MakeListingInputData;

public class MakeListingController {

    private final MakeListingInputBoundary makeListingUseCaseInteractor;
    private MakeListingInputData makeListingInputData;

    public MakeListingController(MakeListingInputBoundary makeListingUseCaseInteractor) {
        this.makeListingUseCaseInteractor = makeListingUseCaseInteractor;
    }

    public void execute(String productName, double price, String filePath, String category, String sellerName) {
        this.makeListingInputData = new MakeListingInputData(productName, price, filePath, category, sellerName);
        makeListingUseCaseInteractor.execute(makeListingInputData);
    }

    public void switchToHomePageView() {
        makeListingInputData = null;
        makeListingUseCaseInteractor.switchToHomePageView();
    }
}