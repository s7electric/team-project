package use_case.search;

/**
 * This interface is implemented by the interactor for the search use case
 * */
public interface SearchInputBoundary {
    /**
     * Executes the interactor with the input data from the controller
     * @param searchInputData the input data that wraps a search text
     * */
    void execute(SearchInputData searchInputData);
    /**
     * Switches to homepage when the state of search has changed
     * */
    void switchToHomepageView();
}
