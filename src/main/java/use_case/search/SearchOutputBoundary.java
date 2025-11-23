package use_case.search;

/**
 * This interface is implemented by the filter presenter
 * */
public interface SearchOutputBoundary {
    /**
     * Updates the state of the search use case with the output data of the search interactor
     * @param searchOutputData the output of the search interactor that wraps a searchText and a list of found products
     * */
    void updateSuccess(SearchOutputData searchOutputData);
    /**
     * Updates the state of the search use case with the output data of the search interactor
     * @param searchOutputData the output of the search interactor that wraps an error message
     * */
    void updateFailure(SearchOutputData searchOutputData);
    /**
     * Switches to homepage when the state of search use case has changed
     * */
    void switchToHomepageView();
}
