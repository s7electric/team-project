package use_case.filter;

public interface FilterInputBoundary {
    void execute(FilterInputData filterInputData);
    void switchToHomepageView();
    void loadProducts();
}
