package use_case.filter;

import entity.Product;

import java.util.List;

/**
 * This interface is implemented by the filter presenter
 * */
public interface FilterOutputBoundary {
    /**
     * Updates the state of filter to contain all the filtered products
     * @param filterOutputData the wrapper which contains the list of the filtered products
     * */
    void updateFilteredProducts(FilterOutputData filterOutputData);
    /**
     * Switches to homepage to show the updated state
     * */
    void switchToHomepageView();
    /**
     * Loads all the products when the app boots up
     * */
    void loadProducts(FilterOutputData filterOutputData);
}
