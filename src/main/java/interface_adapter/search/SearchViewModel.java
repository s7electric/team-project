package interface_adapter.search;

import interface_adapter.ViewModel;

/**
 * ViewModel for the search use case.
 */
public class SearchViewModel extends ViewModel<SearchState> {
    public static final String VIEW_NAME = "Search";

    public SearchViewModel() {
        super(VIEW_NAME);
        setState(new SearchState());
    }
}
