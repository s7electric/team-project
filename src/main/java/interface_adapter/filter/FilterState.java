package interface_adapter.filter;

import entity.Product;

import java.util.List;

/**
 * This class represents the state of the filter use case
 * This class contains a list of filter products
 * */
public class FilterState {
    private List<Product> filteredProducts;

    /**
     * Sets the list of the filtered products
     * @param filteredProducts the list of filtered products
     * */
    public void setFilteredProducts(List<Product> filteredProducts){
        this.filteredProducts = filteredProducts;
    }

    public List<Product> getFilteredProducts(){
        return this.filteredProducts;
    }
}
