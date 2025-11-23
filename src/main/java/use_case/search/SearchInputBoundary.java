package use_case.search;

public interface SearchInputBoundary {
    void execute(SearchInputData searchInputData);
    void switchToHomepageView();
}
