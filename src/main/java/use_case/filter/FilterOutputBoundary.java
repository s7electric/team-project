package use_case.filter;

import entity.Product;

import java.util.List;

public interface FilterOutputBoundary {
    void updateFilteredProducts(FilterOutputData filterOutputData);
    void switchToHomepageView();
    void loadProducts(FilterOutputData filterOutputData);
}
