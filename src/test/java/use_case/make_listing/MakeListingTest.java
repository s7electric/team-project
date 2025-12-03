package use_case.make_listing;

import static org.junit.jupiter.api.Assertions.*;
import data_access.DataAccessObject;
import interface_adapter.make_listing.MakeListingPresenter;
import org.junit.jupiter.api.Test;

public class MakeListingTest {
    @Test
    void successTest() {
        MakeListingInputData makeListingInputData = new MakeListingInputData("steven", 5.5, "C:\\Users\\3speed\\Downloads\\steven.png", "Food", "mc");
        MakeListingDataAccessInterface makeListingDataAccessInterface = new DataAccessObject();
        MakeListingOutputBoundary makeListingOutputBoundary = new MakeListingOutputBoundary() {
            @Override
            public void prepareSuccessView(MakeListingOutputData outputData) {
                assertSame(outputData.getUsername(), makeListingInputData.getSellerName());
                assertEquals(outputData.getMessage(), makeListingInputData.getProductName() + " listed successfully!");
            }

            @Override
            public void prepareFailView(MakeListingOutputData outputData) {
                fail("Failed wrongly");
            }

            @Override
            public void switchToHomePageView() {
                fail("Switched To Home Page");
            }
        };
        MakeListingInputBoundary makeListingInputBoundary = new MakeListingInteractor(makeListingOutputBoundary, makeListingDataAccessInterface);
        makeListingInputBoundary.execute(makeListingInputData);
    }

    @Test
    void failTest() {
        MakeListingInputData makeListingInputData = new MakeListingInputData("steven", 5.5, "C:\\Users\\3speed\\Downloads\\steven.png", "Food", "FakeUser");
        MakeListingDataAccessInterface makeListingDataAccessInterface = new DataAccessObject();
        MakeListingOutputBoundary makeListingOutputBoundary = new MakeListingOutputBoundary() {
            @Override
            public void prepareSuccessView(MakeListingOutputData outputData) {
                fail("Succeeded wrongly");
            }

            @Override
            public void prepareFailView(MakeListingOutputData outputData) {
                assertEquals(outputData.getUsername(), makeListingInputData.getSellerName());
                assertEquals("User does not exist.\n Please try again.", outputData.getMessage());
            }

            @Override
            public void switchToHomePageView() {
                fail("Switched To Home Page");
            }
        };
        MakeListingInputBoundary makeListingInputBoundary = new MakeListingInteractor(makeListingOutputBoundary, makeListingDataAccessInterface);
        makeListingInputBoundary.execute(makeListingInputData);
    }

    @Test
    void switchToHomePageTest() {
        MakeListingDataAccessInterface makeListingDataAccessInterface = new DataAccessObject();
        MakeListingOutputBoundary makeListingOutputBoundary = new MakeListingOutputBoundary() {
            @Override
            public void prepareSuccessView(MakeListingOutputData outputData) {
                fail("Succeeded wrongly");
            }

            @Override
            public void prepareFailView(MakeListingOutputData outputData) {
                fail("Failed wrongly");
            }

            @Override
            public void switchToHomePageView() {
                assert true;
            }
        };
        MakeListingInputBoundary makeListingInputBoundary = new MakeListingInteractor(makeListingOutputBoundary, makeListingDataAccessInterface);
        makeListingInputBoundary.switchToHomePageView();
    }
}
