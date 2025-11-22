package use_case.filter;

import entity.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FilterInteractor implements FilterInputBoundary{
    private FilterOutputBoundary filterPresenter;
    private FilterDataAccessInterface dataAccess;

    public FilterInteractor(FilterOutputBoundary filterPresenter, FilterDataAccessInterface dataAccess){
        this.filterPresenter = filterPresenter;
        this.dataAccess = dataAccess;
    }

    public void execute(FilterInputData filterInputData){
        List<Product> allProducts = dataAccess.getAllProducts();
        List<Product> filteredProducts = new ArrayList<>();
        if (filterInputData.getFilter().equals("All")){
            filteredProducts = allProducts;
        } else if (filterInputData.getFilter().equals("Most Popular")) {
            allProducts.sort((p1, p2) -> Double.compare(p1.getAverageReviewScore(), p2.getAverageReviewScore()));
            filteredProducts = allProducts.reversed();
        } else if (filterInputData.getFilter().equals("Most Expensive")) {
            allProducts.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
            filteredProducts = allProducts.reversed();
        } else if (filterInputData.getFilter().equals("Least Expensive")) {
            allProducts.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
            filteredProducts = allProducts;
        } else {
            for (Product product : allProducts) {
                if (product.getCategory().equalsIgnoreCase(filterInputData.getFilter())) {
                    filteredProducts.add(product);
                }
            }
        }
        FilterOutputData filterOutputData = new FilterOutputData(filteredProducts);
        this.filterPresenter.updateFilteredProducts(filterOutputData);
    }

    public void switchToHomepageView(){
        this.filterPresenter.switchToHomepageView();
    }

    public void loadProducts(){
        List<Product> allProducts = dataAccess.getAllProducts();
        FilterOutputData filterOutputData = new FilterOutputData(allProducts);
        this.filterPresenter.loadProducts(filterOutputData);
    }
}
