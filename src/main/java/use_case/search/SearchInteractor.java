package use_case.search;

import entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the interactor for the search use case
 * This class contains a SearchInteractor and a DataAccess.
 * */
public class SearchInteractor implements SearchInputBoundary{
    private SearchOutputBoundary searchPresenter;
    private SearchDataAccessInterface dataAccess;

    /**
     * creates a SearchInteractor to execute the search computation.
     * @param searchPresenter the presenter for search use case
     * @param dataAccess the dataAccess object for the search use case
     * */
    public SearchInteractor(SearchOutputBoundary searchPresenter, SearchDataAccessInterface dataAccess) {
        this.searchPresenter = searchPresenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Executes the search computation with the input data from the controller for the search use case
     * @param searchInputData the input data that wraps a searchText
     * */
    public void execute(SearchInputData searchInputData) {
        List<Product> allProducts = dataAccess.getAllProducts();
        String[] searchTextSplit = searchInputData.getSearchText().split(" ");
        List<Product> foundProducts = new ArrayList<>();

        // Finds Products that their name or their category matches one of the inputs in the searchText
        for (String searchInput : searchTextSplit) {
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(searchInput.toLowerCase()) || product.getCategory().toLowerCase().contains(searchInput.toLowerCase())) {
                    if (!foundProducts.contains(product)) {
                        foundProducts.add(product);
                    }
                }
            }
        }

        // Output the proper result for the search
        if (foundProducts.isEmpty()) {
            SearchOutputData searchOutputDataError = new SearchOutputData("No match!");
            this.searchPresenter.updateFailure(searchOutputDataError);
        } else {
            SearchOutputData searchOutputDataSuccess = new SearchOutputData(searchInputData.getSearchText(), foundProducts);
            this.searchPresenter.updateSuccess(searchOutputDataSuccess);
        }
    }

    /**
     * Switches to homepage when state of search has changed
     * */
    @Override
    public void switchToHomepageView() {
        this.searchPresenter.switchToHomepageView();
    }
}
