package interface_adapter.Product;

import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageViewModel;
import use_case.open_product.OpenProductOutputBoundary;
import use_case.open_product.OpenProductOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class ProductPresenter implements OpenProductOutputBoundary {
    private final ProductViewModel productViewModel;
    private final HomepageViewModel homepageViewModel;
    private final ViewManagerModel viewManagerModel;

    public ProductPresenter(ViewManagerModel viewManagerModel,
                            ProductViewModel productViewModel,
                            HomepageViewModel homepageViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.productViewModel = productViewModel;
        this.homepageViewModel = homepageViewModel;
    }

    @Override
    public void prepareSuccessView(OpenProductOutputData outputData) {
        // On success, open the Product View
        final ProductState productState = new ProductState();
        productState.setName(outputData.getProductName());
        productState.setProductid(outputData.getProductId());
        productState.setPrice("$" + outputData.getPrice());
        productState.setImageUrl(outputData.getImageURl());
        productState.setSellerName(outputData.getSeller());
        productState.setCategory(outputData.getCategory());
        productState.setRating(String.format("%.1f *", outputData.getAverageReviewScore()));
        productState.setReviewCount(outputData.getTotalReviews() + " reviews");
        productState.setUsername(outputData.getUsername());
        productViewModel.setState(productState);
        viewManagerModel.setState(productViewModel.getViewName());
        viewManagerModel.setActiveViewName(productViewModel.getViewName());
    }

    @Override
    public void switchToHomePageView() {
        this.viewManagerModel.setActiveViewName(this.homepageViewModel.getViewName());
    }

}
