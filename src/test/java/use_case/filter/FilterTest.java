package use_case.filter;

import data_access.DataAccessObject;
import org.junit.jupiter.api.Test;
import use_case.sign_up.*;

import static org.junit.jupiter.api.Assertions.*;

public class FilterTest {
    @Test
    void updateTest() {
        String[] catList = {"All", "Most Popular", "Most Expensive", "Least Expensive", "Food"};
        for (String category : catList) {
            FilterInputData filterInputData = new FilterInputData(category);
            FilterDataAccessInterface filterDataAccessObject = new DataAccessObject();
            FilterOutputBoundary filterOutputBoundary = new FilterOutputBoundary() {
                @Override
                public void updateFilteredProducts(FilterOutputData filterOutputData) {
                    assertTrue(filterOutputData.getFilterCategory() != null);
                    assertEquals(category, filterOutputData.getFilterCategory());
                    assertTrue(filterOutputData.getFilteredProducts() != null);
                }

                @Override
                public void switchToHomepageView() {
                    fail("Use case failure is unexpected");
                }

                public void loadProducts(FilterOutputData filterOutputData) {
                    fail("Use case failure is unexpected");
                }
            };
            FilterInputBoundary filterInputBoundary = new FilterInteractor(filterOutputBoundary, filterDataAccessObject);
            filterInputBoundary.execute(filterInputData);
        }
    }
    @Test
    void testSwitchToHomepageView() {
        FilterDataAccessInterface filterDataAccessObject = new DataAccessObject();
        FilterOutputBoundary filterOutputBoundary = new FilterOutputBoundary() {
            @Override
            public void updateFilteredProducts(FilterOutputData filterOutputData) {
                fail("Use case failure is unexpected");
            }
            @Override
            public void switchToHomepageView() {
                assert true;
            }
            public void loadProducts(FilterOutputData filterOutputData) {
                fail("Use case failure is unexpected");
            }
        };
        FilterInputBoundary filterInputBoundary = new FilterInteractor(filterOutputBoundary, filterDataAccessObject);
        filterInputBoundary.switchToHomepageView();
    }
    @Test
    void testLoadProductsView() {
        FilterDataAccessInterface filterDataAccessObject = new DataAccessObject();
        FilterOutputBoundary filterOutputBoundary = new FilterOutputBoundary() {
            @Override
            public void updateFilteredProducts(FilterOutputData filterOutputData) {
                fail("Use case failure is unexpected");
            }
            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }
            public void loadProducts(FilterOutputData filterOutputData) {
                assertTrue(filterOutputData.getFilterCategory() != null);
                assertEquals("All", filterOutputData.getFilterCategory());
                assertTrue(filterOutputData.getFilteredProducts() != null);
            }
        };
        FilterInputBoundary filterInputBoundary = new FilterInteractor(filterOutputBoundary, filterDataAccessObject);
        filterInputBoundary.loadProducts();
    }
}
