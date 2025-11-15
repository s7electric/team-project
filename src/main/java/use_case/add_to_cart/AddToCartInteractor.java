package use_case.add_to_cart;

import entity.User;

public class AddToCartInteractor implements AddToCartInputBoundary {
    private final AddToCartUserDataAccessInterface userDataAccessObject;
    private final AddToCartOutputBoundary userPresenter;


    public AddToCartInteractor(AddToCartUserDataAccessInterface userDataAccessObject,
                               AddToCartOutputBoundary userPresenter)
    {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }
}
    @Override
    public void execute(AddToCartInputData addToCartInputData) {}
}
