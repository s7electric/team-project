package use_case.filter;

import entity.Product;

import java.util.*;

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
        Map<String, List<Object>> filteredProducts = new LinkedHashMap<>();

        // Gets all products if the filter category is All
        if (filterInputData.getFilter().equals("All")){
            fillProductMapFromList(allProducts, filteredProducts);

        // Gets the most popular products by comparing the average ratings
        } else if (filterInputData.getFilter().equals("Most Popular")) {
            allProducts.sort(Comparator.comparingDouble(Product::getAverageReviewScore).reversed());
            fillProductMapFromList(allProducts, filteredProducts);

        // Gets the most expensive products by comparing their prices
        } else if (filterInputData.getFilter().equals("Most Expensive")) {
            allProducts.sort(Comparator.comparingDouble(Product::getPrice));
            List<Product> newList = new ArrayList<>();
            for (Product product : allProducts) {
                newList.add(0, product);
            }
            fillProductMapFromList(newList, filteredProducts);

        // Gets the least expensive products by comparing their prices
        } else if (filterInputData.getFilter().equals("Least Expensive")) {
            allProducts.sort(Comparator.comparingDouble(Product::getPrice));
            fillProductMapFromList(allProducts, filteredProducts);

        // Gets the products associated with the user-specified category in the filter input data
        } else {
            for (Product product : allProducts) {
                if (product.getCategory().equalsIgnoreCase(filterInputData.getFilter())) {
                    if (!filteredProducts.containsKey(product.getProductUUID())){
                        filteredProducts.put(product.getProductUUID(), new ArrayList<>(Arrays.asList(product.getName(), product.getimageBase64(), product.getPrice())));
                    }
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
        Map<String, List<Object>> filteredProducts = new LinkedHashMap<>();
        fillProductMapFromList(allProducts, filteredProducts);
        FilterOutputData filterOutputData = new FilterOutputData("All", filteredProducts);
        this.filterPresenter.loadProducts(filterOutputData);
    }

    private static void fillProductMapFromList(List<Product> allProducts, Map<String, List<Object>> filteredProducts) {
        for (Product p: allProducts){
            if (!filteredProducts.containsKey(p.getProductUUID())){
                filteredProducts.put(p.getProductUUID(), new ArrayList<>(Arrays.asList(p.getName(), p.getimageBase64(), p.getPrice())));
            }
        }
    }
}
