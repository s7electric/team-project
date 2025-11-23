package use_case.filter;

import entity.Product;

import java.util.List;

/**
 * This class represents the output data of the filter interactor.
 * This class contains a list of filtered products, a filter category.
 */
public class FilterOutputData {
    private List<Product> filteredProducts;
    private String filterCategory;

    /**
     * Creates a FilterOutputData object to wrap the list of the filtered products and the filter category.
     * @param filteredProducts the list of filtered products
     * @param filterCategory the name of filter category
     * */
    public FilterOutputData(String filterCategory, List<Product> filteredProducts){
        this.filteredProducts = filteredProducts;
        this.filterCategory = filterCategory;
    }

    public List<Product> getFilteredProducts(){
        return filteredProducts;
    }

    public String getFilterCategory(){
        return filterCategory;
    }
}
