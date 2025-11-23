package use_case.open_product;

import entity.Product;

public class OpenProductInteractor implements OpenProductInputBoundary {
    private final OpenProductProductDataAccessInterface userDataAccessObject;
    private final OpenProductOutputBoundary userPresenter;
    public OpenProductInteractor(OpenProductProductDataAccessInterface userDataAccessObject,OpenProductOutputBoundary userPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }

    @Override
    public void execute(OpenProductInputData openProductInputData) {
        int productId = openProductInputData.getProductId();
        Product showedProduct = userDataAccessObject.getProduct(productId);
        OpenProductOutputData openProductOutputData = new OpenProductOutputData(showedProduct.getName(),showedProduct.getPrice(),showedProduct.getProductid(),showedProduct.getImageUrl(),showedProduct.getUser(),showedProduct.getCategory(),showedProduct.getAverageReviewScore());
        userPresenter.prepareSuccessView(openProductOutputData);
    }
}
