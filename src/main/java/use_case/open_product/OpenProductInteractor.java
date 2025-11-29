package use_case.open_product;

import entity.Product;
import interface_adapter.Product.ProductPresenter;

public class OpenProductInteractor implements OpenProductInputBoundary {
    private final OpenProductProductDataAccessInterface userDataAccessObject;
    private final OpenProductOutputBoundary userPresenter;
    public OpenProductInteractor(OpenProductProductDataAccessInterface userDataAccessObject,OpenProductOutputBoundary userPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }

    @Override
    public void execute(OpenProductInputData openProductInputData) {
        String productUUID = openProductInputData.getProductId();
        Product showedProduct = userDataAccessObject.getProduct(productUUID);
        OpenProductOutputData openProductOutputData = new OpenProductOutputData(showedProduct.getName(),showedProduct.getPrice(),showedProduct.getProductUUID(),showedProduct.getImageUrl(),showedProduct.getUser().getUsername(),showedProduct.getCategory(),showedProduct.getAverageReviewScore(),showedProduct.getScores().size(), openProductInputData.getUsername());
        userPresenter.prepareSuccessView(openProductOutputData);
    }

    @Override
    public void switchToHomePageView() {
        userPresenter.switchToHomePageView();
    }

}
