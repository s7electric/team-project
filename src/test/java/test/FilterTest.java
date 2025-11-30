package test;

import data_access.DataAccessObject;
import use_case.filter.*;

import static org.junit.jupiter.api.Assertions.*;

public class FilterTest {
    void updateTest(String filter){
        FilterInputData filterInputData = new FilterInputData(filter);
        FilterDataAccessInterface filterDataAccessObject = new DataAccessObject();
        FilterOutputBoundary filterOutputBoundary = new FilterOutputBoundary() {
            @Override
            public void updateFilteredProducts(FilterOutputData filterOutputData) {
                assertTrue(filterOutputData.getFilterCategory() != null);
                assertEquals(filter,  filterOutputData.getFilterCategory());
                assertTrue(filterOutputData.getFilteredProducts() != null);
            }

            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }

            public void loadProducts(FilterOutputData filterOutputData){
                fail("Use case failure is unexpected");
            }
        };
        FilterInputBoundary filterInputBoundary = new FilterInteractor(filterOutputBoundary, filterDataAccessObject);
        filterInputBoundary.execute(filterInputData);
    }

    public static void main(String[] args) {
        FilterTest filterTest = new FilterTest();
        filterTest.updateTest("All");
    }
}
