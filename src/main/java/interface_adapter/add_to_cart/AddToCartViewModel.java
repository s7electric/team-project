package interface_adapter.add_to_cart;

import interface_adapter.ViewModel;
import use_case.add_to_cart.AddToCartInputData;

/**
 * The View Model for the Add to Cart Use Case.
 */
public class AddToCartViewModel extends ViewModel<AddToCartState> {
    public AddToCartViewModel() {
        super("add to cart");
        setState(new AddToCartState());
    }

}
