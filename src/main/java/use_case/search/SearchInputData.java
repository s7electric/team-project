package use_case.search;

public class SearchInputData {
    private final String searchText;
    public SearchInputData(String searchText) {
        this.searchText = searchText;
    }
    public String getSearchText() {
        return searchText;
    }
}
