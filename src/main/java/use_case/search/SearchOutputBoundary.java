package use_case.search;

public interface SearchOutputBoundary {
    void updateSuccess(SearchOutputData searchOutputData);
    void updateFailure(SearchOutputData searchOutputData);
    void switchToHomepageView();
}
