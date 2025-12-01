package use_case.open_product;

import entity.Product;

public class OpenProductInteractor implements OpenProductInputBoundary {
    private final OpenProductProductDataAccessInterface userDataAccessObject;
    private final OpenProductOutputBoundary userPresenter;

    public OpenProductInteractor(OpenProductProductDataAccessInterface userDataAccessObject,
                                 OpenProductOutputBoundary userPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }

    @Override
    public void execute(OpenProductInputData openProductInputData) {
        final String productId = openProductInputData.getProductId();
        final Product showedProduct = userDataAccessObject.getProduct(productId);
        final OpenProductOutputData openProductOutputData = new OpenProductOutputData(
                showedProduct.getName(),
                showedProduct.getPrice(),
                showedProduct.getProductUUID(),
                showedProduct.getImageUrl(),
                showedProduct.getUser().getUsername(),
                showedProduct.getCategory(),
                showedProduct.getAverageReviewScore(),
                showedProduct.getScores().size(),
                openProductInputData.getUsername());
        userPresenter.prepareSuccessView(openProductOutputData);
    }

    @Override
    public void switchToHomePageView() {
        userPresenter.switchToHomePageView();
    }

}
