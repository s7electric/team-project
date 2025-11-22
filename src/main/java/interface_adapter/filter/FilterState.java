package interface_adapter.filter;

import entity.Product;

import java.util.List;

public class FilterState {
    private String currentFilter;
    private List<Product> filteredProducts;
    public void setFilter(String filter){
        this.currentFilter = filter;
    }
    public void setFilteredProducts(List<Product> filteredProducts){
        this.filteredProducts = filteredProducts;
    }
    public String getCurrentFilter(){
        return this.currentFilter;
    }
    public List<Product> getFilteredProducts(){
        return this.filteredProducts;
    }
}
