package use_case.search;

import entity.Product;

import java.util.List;

/**
 * This class represents the output data of the filter interactor.
 * This class contains a searchText, a list of found products and an error message.
 * */
public class SearchOutputData {
    private String searchText;
    private List<Product> foundProducts;
    private String error;

    /**
     * Creates a SearchOutputData that wraps a searchText, and a list of found products.
     * @param searchText the search text that the user inputted
     * @param foundProducts the list of the products that match the search text
     * */
    public SearchOutputData(String searchText,  List<Product> foundProducts) {
        this.searchText = searchText;
        this.foundProducts = foundProducts;
    }

    /**
     * Creates a SearchOutputData that wraps an error message
     * @param error the error message when no product is found
     * */
    public SearchOutputData(String error) {
        this.searchText = error;
    }

    public String getError(){
        return this.error;
    }
    public String getSearchText() {
        return searchText;
    }

    public List<Product> getFoundProducts() {
        return foundProducts;
    }
}
