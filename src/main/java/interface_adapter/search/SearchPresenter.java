package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

/**
 * Presenter for the search use case.
 */
public class SearchPresenter implements SearchOutputBoundary {

    private final SearchViewModel searchViewModel;
    private final HomepageViewModel homepageViewModel;
    private final HomepageState homepageState;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(SearchViewModel searchViewModel,
                           HomepageViewModel homepageViewModel,
                           HomepageState homepageState,
                           ViewManagerModel viewManagerModel) {
        this.searchViewModel = searchViewModel;
        this.homepageViewModel = homepageViewModel;
        this.homepageState = homepageState;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void updateSuccess(SearchOutputData outputData) {
        homepageState.setProducts(outputData.getFoundProducts());
        homepageState.setSearchText(outputData.getSearchText());
        homepageViewModel.setState(homepageState);
        searchViewModel.setState(new SearchState()); // clear error
        viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
    }

    @Override
    public void updateFailure(SearchOutputData outputData) {
        SearchState state = new SearchState();
        state.setFailure(outputData.getError());
        searchViewModel.setState(state);
    }

    @Override
    public void switchToHomepageView() {
        viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
    }
}
