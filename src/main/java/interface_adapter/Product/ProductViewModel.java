package interface_adapter.Product;

import interface_adapter.ViewModel;

public class ProductViewModel extends ViewModel<ProductState> {
    /**
     * Creates a FilterViewModel object to update the filter view.
     * */

    public ProductViewModel() {
        super("Product");
        setState(new ProductState());
    }
}
