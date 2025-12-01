package test;

import entity.Product;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.open_product.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductInfoTest {

    @Test
    public void testExecute() {
        User seller = new User(
                "sellerUser",
                "seller@mail.com",
                "password123",
                "123 Street"
        );
        Product product = new Product(
                "TestProduct",
                19.99,
                "uuid-123",
                "http://image.png",
                seller,
                "Electronics"
        );
        product.addScore(5);
        product.addScore(3);
        OpenProductProductDataAccessInterface dao = new OpenProductProductDataAccessInterface() {
            @Override
            public Product getProduct(String uuid) {
                assertEquals("uuid-123", uuid);
                return product;
            }
        };
        class PresenterStub implements OpenProductOutputBoundary {
            OpenProductOutputData receivedData = null;

            @Override
            public void prepareSuccessView(OpenProductOutputData data) {
                this.receivedData = data;
            }

            @Override
            public void switchToHomePageView() {}
        }
        PresenterStub presenter = new PresenterStub();
        OpenProductInteractor interactor = new OpenProductInteractor(dao, presenter);
        OpenProductInputData input = new OpenProductInputData("uuid-123", "buyerUser");
        interactor.execute(input);
        assertNotNull(presenter.receivedData);
        assertEquals("TestProduct", presenter.receivedData.getProductName());
        assertEquals(19.99, presenter.receivedData.getPrice());
        assertEquals("uuid-123", presenter.receivedData.getProductId());
        assertEquals("http://image.png", presenter.receivedData.getImageURl());
        assertEquals("sellerUser", presenter.receivedData.getSeller());
        assertEquals("Electronics", presenter.receivedData.getCategory());
        // average score = (5 + 3) / 2 = 4.0
        assertEquals(4.0, product.getAverageReviewScore(), 0.0001);
        assertEquals(2, presenter.receivedData.getTotalReviews());

        assertEquals("buyerUser", presenter.receivedData.getUsername());
    }

    @Test
    public void testSwitchToHomePageView() {
        OpenProductProductDataAccessInterface dummyDao = uuid -> null;
        class PresenterStub implements OpenProductOutputBoundary {
            boolean called = false;
            @Override
            public void prepareSuccessView(OpenProductOutputData data) {}

            @Override
            public void switchToHomePageView() {
                called = true;
            }
        }
        PresenterStub presenter = new PresenterStub();
        OpenProductInteractor interactor = new OpenProductInteractor(dummyDao, presenter);
        interactor.switchToHomePageView();
        assertTrue(presenter.called);
    }
}