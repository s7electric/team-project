package use_case.search;

import entity.Product;

import java.util.List;
import java.util.Map;

/**
 * This class represents the output data of the filter interactor.
 * This class contains a searchText, a map of found products and an error message.
 * */
public class SearchOutputData {
    private String searchText;
    private Map<String, List<Object>> foundProducts;
    private String error;

    /**
     * Creates a SearchOutputData that wraps a searchText, and a map of found products.
     * @param searchText the search text that the user inputted
     * @param foundProducts the map of the products that match the search text
     * */
    public SearchOutputData(String searchText,  Map<String, List<Object>> foundProducts) {
        this.searchText = searchText;
        this.foundProducts = foundProducts;
    }

    /**
     * Creates a SearchOutputData that wraps an error message
     * @param error the error message when no product is found
     * */
    public SearchOutputData(String error) {
        this.error = error;
    }

    public String getError(){
        return this.error;
    }
    public String getSearchText() {
        return searchText;
    }

    public Map<String, List<Object>> getFoundProducts() {
        return foundProducts;
    }
}
