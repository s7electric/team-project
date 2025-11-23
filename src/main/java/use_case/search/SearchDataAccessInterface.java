package use_case.search;

import entity.Product;

import java.util.List;

/**
 * This interface is implemented by the DataAccess class
 * */
public interface SearchDataAccessInterface {
    List<Product> getAllProducts();
}
