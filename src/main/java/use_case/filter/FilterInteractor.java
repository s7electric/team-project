package use_case.filter;

import entity.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class performs all the filtering computations.
 * This class contains a FilterPresenter and a FilterDataAccess.
 * */
public class FilterInteractor implements FilterInputBoundary{
    private FilterOutputBoundary filterPresenter;
    private FilterDataAccessInterface dataAccess;

    /**
     * Creates a FilterInteractor object to perform filter computations
     * @param filterPresenter the presenter for the filter use case
     * @param dataAccess the data access object for fetching data from the database
     * */
    public FilterInteractor(FilterOutputBoundary filterPresenter, FilterDataAccessInterface dataAccess){
        this.filterPresenter = filterPresenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Executes the filter use case and filters out products based on the specified filter category in filter input data.
     * @param filterInputData the input data that wraps the filter category
     * */
    public void execute(FilterInputData filterInputData){
        List<Product> allProducts = dataAccess.getAllProducts();
        List<Product> filteredProducts = new ArrayList<>();

        // Gets all products if the filter category is All
        if (filterInputData.getFilter().equals("All")){
            filteredProducts = allProducts;

        // Gets the most popular products by comparing the average ratings
        } else if (filterInputData.getFilter().equals("Most Popular")) {
            allProducts.sort((p1, p2) -> Double.compare(p1.getAverageReviewScore(), p2.getAverageReviewScore()));
            filteredProducts = allProducts.reversed();

        // Gets the most expensive products by comparing their prices
        } else if (filterInputData.getFilter().equals("Most Expensive")) {
            allProducts.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
            filteredProducts = allProducts.reversed();

        // Gets the least expensive products by comparing their prices
        } else if (filterInputData.getFilter().equals("Least Expensive")) {
            allProducts.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
            filteredProducts = allProducts;

        // Gets the products associated with the user-specified category in the filter input data
        } else {
            for (Product product : allProducts) {
                if (product.getCategory().equalsIgnoreCase(filterInputData.getFilter())) {
                    filteredProducts.add(product);
                }
            }
        }
        FilterOutputData filterOutputData = new FilterOutputData(filterInputData.getFilter(), filteredProducts);
        this.filterPresenter.updateFilteredProducts(filterOutputData);
    }

    /**
     * Switches to homepage view when the state has changed
     * */
    public void switchToHomepageView(){
        this.filterPresenter.switchToHomepageView();
    }

    /**
     * Loads all the products when the app boots up
     * */
    public void loadProducts(){
        List<Product> allProducts = dataAccess.getAllProducts();
        FilterOutputData filterOutputData = new FilterOutputData("All", allProducts);
        this.filterPresenter.loadProducts(filterOutputData);
    }
}
