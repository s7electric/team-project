package interface_adapter.homepage;

import entity.Product;

public class HomepageState {
    private String username;
    private Product[] products;

    public HomepageState(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public Product[] getProducts() {
        return this.products;
    }
}