package use_case.add_to_cart;

import entity.User;

public class AddToCartInteractor implements AddToCartInputBoundary {
    private final AddToCartUserDataAccessInterface userDataAccessObject;
    private final AddToCartOutputBoundary userPresenter;

    public AddToCartInteractor(AddToCartUserDataAccessInterface userDataAccessObject, AddToCartOutputBoundary userPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }
    @Override
    public void execute(AddToCartInputData addToCartInputData) {
        if (addToCartInputData.getQuantity() <= 0){
            userPresenter.prepareFailureView("Quantity should be greater than 0");
        }
        else {
            final User user = userDataAccessObject.getUserData(addToCartInputData.getUser());
            userDataAccessObject.addToCart(user,addToCartInputData.getProductId(),addToCartInputData.getQuantity());

            final AddToCartOutputData addToCartOutputData = new AddToCartOutputData(user.getUsername(),user.getCart().getTotalQuantity());
            userPresenter.prepareSuccessView(addToCartOutputData);
        }
    }
}
