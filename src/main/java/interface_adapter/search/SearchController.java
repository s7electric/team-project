package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

/**
 * Controller for the search use case.
 */
public class SearchController {
    private final SearchInputBoundary searchInteractor;

    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    public void execute(String searchText) {
        SearchInputData inputData = new SearchInputData(searchText);
        searchInteractor.execute(inputData);
    }

    public void switchToHomepageView() {
        searchInteractor.switchToHomepageView();
    }
}
