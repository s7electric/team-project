package use_case.filter;

import entity.Product;

import java.util.List;
import java.util.Map;

/**
 * This class represents the output data of the filter interactor.
 * This class contains a map of filtered products, a filter category.
 */
public class FilterOutputData {
    private Map<String, List<Object>> filteredProducts;
    private String filterCategory;

    /**
     * Creates a FilterOutputData object to wrap the map of the filtered products and the filter category.
     * @param filteredProducts the map of filtered products
     * @param filterCategory the name of filter category
     * */
    public FilterOutputData(String filterCategory, Map<String, List<Object>> filteredProducts){
        this.filteredProducts = filteredProducts;
        this.filterCategory = filterCategory;
    }

    public Map<String, List<Object>> getFilteredProducts(){
        return filteredProducts;
    }

    public String getFilterCategory(){
        return filterCategory;
    }
}
