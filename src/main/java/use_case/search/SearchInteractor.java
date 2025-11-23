package use_case.search;

import entity.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchInteractor implements SearchInputBoundary{
    private SearchOutputBoundary searchPresenter;
    private SearchDataAccessInterface dataAccess;

    public SearchInteractor(SearchOutputBoundary searchPresenter, SearchDataAccessInterface dataAccess) {
        this.searchPresenter = searchPresenter;
        this.dataAccess = dataAccess;
    }

    public void execute(SearchInputData searchInputData) {
        List<Product> allproducts = dataAccess.getAllProducts();
        String[] searchTextSplited = searchInputData.getSearchText().split(" ");
        List<Product> foundProducts = new ArrayList<>();
        for (String searchInput : searchTextSplited) {
            for (Product product : allproducts) {
                if (product.getName().toLowerCase().contains(searchInput.toLowerCase()) || product.getCategory().toLowerCase().contains(searchInput.toLowerCase())) {
                    foundProducts.add(product);
                }
            }
        }
        if (foundProducts.isEmpty()) {
            SearchOutputData searchOutputDataError = new SearchOutputData("No match!");
            this.searchPresenter.updateFailure(searchOutputDataError);
        } else {
            SearchOutputData searchOutputDataSuccess = new SearchOutputData(searchInputData.getSearchText(), foundProducts);
            this.searchPresenter.updateSuccess(searchOutputDataSuccess);
        }
    }

    @Override
    public void switchToHomepageView() {
        this.searchPresenter.switchToHomepageView();
    }
}
