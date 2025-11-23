package use_case.search;

import entity.Product;

import java.util.List;

public class SearchOutputData {
    private String searchText;
    private List<Product> foundProducts;
    private String error;
    public SearchOutputData(String searchText,  List<Product> foundProducts) {
        this.searchText = searchText;
        this.foundProducts = foundProducts;
    }
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
