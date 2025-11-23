package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

/**
 * This class represents the controller for the search use case
 * It contains a search interactor
 * */
public class SearchController {
    private SearchInputBoundary searchInteractor;

    /**
     * Creates a SearchController object for the search use case
     * @param searchInteractor the interactor for the search use case
     * */
    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    /**
     * Executes the interactor with an input data object
     * @param searchText the searchText that the user inputted
     * */
    public void execute(String searchText) {
        SearchInputData searchInputData = new SearchInputData(searchText);
        searchInteractor.execute(searchInputData);
    }

    /**
     * Switches to homepage when the state of the search has changed
     * */
    public void switchToHomepageView(){
        searchInteractor.switchToHomepageView();
    }
}
