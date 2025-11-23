package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

public class SearchController {
    private SearchInputBoundary searchInteractor;

    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    public void execute(String searchText) {
        SearchInputData searchInputData = new SearchInputData(searchText);
        searchInteractor.execute(searchInputData);
    }

    public void switchToHomepageView(){
        searchInteractor.switchToHomepageView();
    }
}
