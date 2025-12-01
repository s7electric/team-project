package interface_adapter.Product;

import use_case.open_product.OpenProductInputBoundary;
import use_case.open_product.OpenProductInputData;
/**
 * This class represents the controller for the product use case.
 * */

public class ProductController {
    private OpenProductInputBoundary openProductUseCaseInteractor;

    public ProductController(OpenProductInputBoundary openProductUseCaseInteractor) {
        this.openProductUseCaseInteractor = openProductUseCaseInteractor;
    }
    /**
     * Executes the Product Use Case.
     * @param productid the product that the user clicks on
     * @param username the user viewing the product
     */

    public void execute(String productid, String username) {
        final OpenProductInputData openProductInputData = new OpenProductInputData(productid, username);
        openProductUseCaseInteractor.execute(openProductInputData);
    }
    /**
     * Switches back to the home page view.
     */

    public void switchToHomePageView() {
        openProductUseCaseInteractor.switchToHomePageView();
    }
}
