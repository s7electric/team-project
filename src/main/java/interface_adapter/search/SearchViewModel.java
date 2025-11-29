package interface_adapter.search;

import interface_adapter.ViewModel;
import view.SearchView;

/**
 * This class represents the view moel for the search use case
 * */
public class SearchViewModel extends ViewModel<SearchState> {
    /**
     * Creates a SearchViewModel object to update the view for the search use case
     * */
    public SearchViewModel(){
        super("Search");
        setState(new SearchState());
    }
}
