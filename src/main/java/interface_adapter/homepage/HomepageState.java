package interface_adapter.homepage;

import entity.Product;

import java.util.List;
import java.util.Map;

public class HomepageState {
    private String username;
    private String searchText;
    private Map<String, List<Object>> products;

    public HomepageState(String username) {
        this.username = username;
        this.searchText = "";
        this.products = null;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setProducts(Map<String, List<Object>> products) {
        this.products = products;
    }

    public Map<String, List<Object>> getProducts() {
        return this.products;
    }
}
