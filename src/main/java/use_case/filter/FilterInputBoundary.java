package use_case.filter;

public interface FilterInputBoundary {
    void addFilter(FilterInputData filterInputData);
    void removeFilter(FilterInputData filterInputData);
    void switchtoLoggedInView();
    void switchToLoggedOutView();
}
