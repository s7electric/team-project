package interface_adapter.search;

import view.SearchView;

/**
 * This class represents the view moel for the search use case
 * */
public class SearchViewModel extends SearchView<SearchState>{
    /**
     * Creates a SearchViewModel object to update the view for the search use case
     * */
    public SearchViewModel(){
        super("Search");
        setState(new SearchState());
    }
}
