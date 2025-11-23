package use_case.filter;

import entity.Product;

import java.util.List;

/**
 * This interface is implemented by the data access class
 * */
public interface FilterDataAccessInterface {
    List<Product> getAllProducts();
}
