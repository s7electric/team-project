package test;

import data_access.DataAccessObject;
import use_case.search.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest{
    void successTest(String searchText){
        SearchInputData searchInputData = new SearchInputData(searchText);
        SearchDataAccessInterface searchDataAccessObject = new DataAccessObject();
        SearchOutputBoundary searchOutputBoundary = new SearchOutputBoundary() {
            @Override
            public void updateSuccess(SearchOutputData searchOutputData) {
                assertTrue(searchOutputData.getSearchText() != null);
                assertEquals(searchText, searchOutputData.getSearchText());
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

    void failureTest(String searchText){
        SearchInputData searchInputData = new SearchInputData(searchText);
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

    public static void main(String[] args) {
        SearchTest searchTest = new SearchTest();
        searchTest.successTest("cheese");
        searchTest.failureTest("failure");
    }
}