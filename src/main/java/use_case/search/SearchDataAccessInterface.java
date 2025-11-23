package use_case.search;

import entity.Product;

import java.util.List;

public interface SearchDataAccessInterface {
    List<Product> getAllProducts();
}
