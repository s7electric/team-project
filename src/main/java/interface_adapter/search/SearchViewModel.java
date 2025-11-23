package interface_adapter.search;

import view.SearchView;

public class SearchViewModel extends SearchView<SearchState>{
    public SearchViewModel(){
        super("Search");
        setState(new SearchState());
    }
}
