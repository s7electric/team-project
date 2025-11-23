package interface_adapter.search;

import entity.Product;

import java.util.List;

public class SearchState {
    private String searchText;
    private String error;
    private List<Product> foundProducts;

    public void setSuccess(String searchText, List<Product> foundProducts){
        this.searchText = searchText;
        this.foundProducts = foundProducts;
    }

    public void setFailure(String error){
        this.error = error;
    }

    public String getSearchTextSuccess(){
        return this.searchText;
    }

    public String getErrorFailure(){
        return this.error;
    }

    public List<Product> getFoundProductsSuccess(){
        return this.foundProducts;
    }
}
