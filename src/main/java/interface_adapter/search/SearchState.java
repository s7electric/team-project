package interface_adapter.search;

import entity.Product;

import java.util.List;

/**
 * This class represents the state for the search use case.
 * It contains a searchText, a list of found products or an error message
 * */
public class SearchState {
    private String searchText;
    private String error;
    private List<Product> foundProducts;

    /**
     * Sets the state to show the process was a success
     * @param searchText the searchText that the user inputted
     * @param foundProducts the list of products that match the searchText
     * */
    public void setSuccess(String searchText, List<Product> foundProducts){
        this.searchText = searchText;
        this.foundProducts = foundProducts;
    }

    /**
     * Set the state to show the process was a failure
     * @param error the error message for when no product matches the searchText
     * */
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
