package use_case.filter;

import entity.Product;

import java.util.List;

/**
 * This class represents the output data of the filter interactor.
 * This class contains a list of filtered products.
 */
public class FilterOutputData {
    private List<Product> filteredProducts;

    /**
     * Creates a FilterOutputData object to wrap the list of the filtered products.
     * @param filteredProducts the list of filtered products
     * */
    public FilterOutputData(List<Product> filteredProducts){
        this.filteredProducts = filteredProducts;
    }

    public List<Product> getFilteredProducts(){
        return filteredProducts;
    }
}
