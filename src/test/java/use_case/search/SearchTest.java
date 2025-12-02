package use_case.search;

import data_access.DataAccessObject;
import org.junit.jupiter.api.Test;
import use_case.sign_up.*;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest{
    @Test
    void successTest(){
        SearchInputData searchInputData = new SearchInputData("cheese");
        SearchDataAccessInterface searchDataAccessObject = new DataAccessObject();
        SearchOutputBoundary searchOutputBoundary = new SearchOutputBoundary() {
            @Override
            public void updateSuccess(SearchOutputData searchOutputData) {
                assertTrue(searchOutputData.getSearchText() != null);
                assertEquals("cheese", searchOutputData.getSearchText());
                assertTrue(searchOutputData.getError() == null);
                assertTrue(searchOutputData.getFoundProducts() != null);
            }

            @Override
            public void updateFailure(SearchOutputData searchOutputData) {
                fail("Use case failure is unexpected");
            }

            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }
        };
        SearchInputBoundary searchInputBoundary = new SearchInteractor(searchOutputBoundary, searchDataAccessObject);
        searchInputBoundary.execute(searchInputData);
    }
    @Test
    void failureTest(){
        SearchInputData searchInputData = new SearchInputData("failure");
        SearchDataAccessInterface searchDataAccessObject = new DataAccessObject();
        SearchOutputBoundary searchOutputBoundary = new SearchOutputBoundary() {
            @Override
            public void updateSuccess(SearchOutputData searchOutputData) {
                fail("Use case failure is unexpected");
            }

            @Override
            public void updateFailure(SearchOutputData searchOutputData) {
                assertTrue(searchOutputData.getSearchText() == null);
                assertTrue(searchOutputData.getError() != null);
                assertEquals("No match!",  searchOutputData.getError());
                assertTrue(searchOutputData.getFoundProducts() == null);
            }

            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }
        };
        SearchInputBoundary searchInputBoundary = new SearchInteractor(searchOutputBoundary, searchDataAccessObject);
        searchInputBoundary.execute(searchInputData);
    }
    @Test
    void testSwitchToHomepageView() {
        SearchDataAccessInterface searchDataAccessObject = new DataAccessObject();
        SearchOutputBoundary searchOutputBoundary = new SearchOutputBoundary() {
            @Override
            public void updateSuccess(SearchOutputData searchOutputData) {
                fail("Use case failure is unexpected");
            }
            @Override
            public void updateFailure(SearchOutputData searchOutputData) {
                fail("Use case failure is unexpected");
            }
            @Override
            public void switchToHomepageView() {
                assert true;
            }
        };
        SearchInputBoundary searchInputBoundary = new SearchInteractor(searchOutputBoundary, searchDataAccessObject);
        searchInputBoundary.switchToHomepageView();
    }
}