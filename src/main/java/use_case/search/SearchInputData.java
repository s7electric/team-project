package use_case.search;

/**
 * This class represents the input data for the search use case
 * This class contains a searchText
 * */
public class SearchInputData {
    private final String searchText;

    /**
     * Creates a SearchInputData with an input data
     * @param searchText the search text that user inputted
     * */
    public SearchInputData(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }
}
