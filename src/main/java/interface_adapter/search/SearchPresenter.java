package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;
import interface_adapter.homepage.*;

/**
 * This class represents the presenter for the search use case
 * This class contains a search view model, a search state, a view manager model, a homepage view model, a homepage state
 * */
public class SearchPresenter implements SearchOutputBoundary {
    private SearchViewModel searchViewModel;
    private SearchState searchState;
    private ViewManagerModel viewManagerModel;
    private HomepageViewModel homepageViewModel;
    private HomepageState homepageState;

    /**
     * Creates a SearchPresenter object to update the view for the search use case
     * @param searchViewModel the view model for the search use case
     * @param searchState the state for the search use case
     * @param viewManagerModel the view manager model for the app
     * @param homepageState the state of the homepage
     * @param homepageViewModel the view model of the homepage
     * */
    public SearchPresenter(SearchViewModel searchViewModel, SearchState searchState, ViewManagerModel viewManagerModel, HomepageViewModel homepageViewModel, HomepageState homepageState) {
        this.searchViewModel = searchViewModel;
        this.searchState = searchState;
        this.viewManagerModel = viewManagerModel;
        this.homepageViewModel = homepageViewModel;
        this.homepageState = homepageState;
    }

    /**
     * Updates the view for the search use case based on if the process was a success
     * @param searchOutputData the search output data that wraps a searchText and a list of found products
     * */
    public void updateSuccess(SearchOutputData searchOutputData){
        this.searchState.setSuccess(searchOutputData.getSearchText(), searchOutputData.getFoundProducts());
        this.searchState.setFailure(null);
        this.searchViewModel.setState(this.searchState);
    }

    /**
     * Updates the view for the search use case based on if the process was a failure
     * @param searchOutputData the search output data that wraps an error message
     * */
    public void updateFailure(SearchOutputData searchOutputData){
        this.searchState.setSuccess(null, null);
        this.searchState.setFailure(searchOutputData.getError());
        this.searchViewModel.setState(this.searchState);
    }

    /**
     * Switches to homepage when the state of the search has changed
     * */
    public void switchToHomepageView(){
        this.homepageState.setSearchText(this.searchState.getSearchTextSuccess());
        this.homepageState.setProducts(this.searchState.getFoundProductsSuccess());
        this.homepageViewModel.setState(this.homepageState);
        this.searchState.setSuccess(null, null);
        this.searchState.setFailure(null);
        this.viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
    }
}
