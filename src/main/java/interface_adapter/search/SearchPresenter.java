package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

public class SearchPresenter implements SearchOutputBoundary {
    private SearchViewModel searchViewModel;
    private SearchState searchState;
    private ViewManagerModel viewManagerModel;
    private HomepageViewModel homepageViewModel;
    private HomepageState homepageState;

    public SearchPresenter(SearchViewModel searchViewModel, SearchState searchState, ViewManagerModel viewManagerModel, HomepageViewModel homepageViewModel, HomepageState homepageState) {
        this.searchViewModel = searchViewModel;
        this.searchState = searchState;
        this.viewManagerModel = viewManagerModel;
        this.homepageViewModel = homepageViewModel;
        this.homepageState = homepageState;
    }

    public void updateSuccess(SearchOutputData searchOutputData){
        this.searchState.setSuccess(searchOutputData.getSearchText(), searchOutputData.getFoundProducts());
        this.searchState.setFailure(null);
        this.searchViewModel.setState(this.searchState);
    }

    public void updateFailure(SearchOutputData searchOutputData){
        this.searchState.setSuccess(null, null);
        this.searchState.setFailure(searchOutputData.getError());
        this.searchViewModel.setstate(this.searchState);
    }

    public void switchToHomepageView(){
        this.homepageState.setSearchText(this.searchState.getSearchTextSuccess());
        this.homepageState.setFoundProducts(this.searchState.getFoundProductsSuccess());
        this.searchState.setSuccess(null, null);
        this.searchState.setFailure(null);
        this.viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
    }
}
