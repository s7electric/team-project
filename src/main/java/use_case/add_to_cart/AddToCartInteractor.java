package use_case.add_to_cart;

import entity.Product;
import entity.User;

public class AddToCartInteractor implements AddToCartInputBoundary {
    private final AddToCartUserDataAccessInterface userDataAccessObject;
    private final AddToCartProductDataAccessInterface productDataAccessObject;
    private final AddToCartOutputBoundary userPresenter;

    public AddToCartInteractor(AddToCartUserDataAccessInterface userDataAccessObject, AddToCartOutputBoundary userPresenter, AddToCartProductDataAccessInterface productDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
        this.productDataAccessObject = productDataAccessObject;
        this.userPresenter = userPresenter;
    }
    @Override
    public void execute(AddToCartInputData addToCartInputData) {
        if (addToCartInputData.getQuantity() <= 0){
            userPresenter.prepareFailureView("Quantity should be greater than 0");
        }
        else {
            final User user = userDataAccessObject.getUserData(addToCartInputData.getUser());
            final Product product = productDataAccessObject.getProduct(addToCartInputData.getProductUUID());
            userDataAccessObject.addToCart(user,addToCartInputData.getProductUUID(),addToCartInputData.getQuantity());
            final AddToCartOutputData addToCartOutputData = new AddToCartOutputData(product.getProductUUID(),product.getName(),user.getCart().getTotalQuantity());
            userPresenter.prepareSuccessView(addToCartOutputData);
        }
    }
}
