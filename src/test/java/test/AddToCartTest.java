package test;

import entity.Product;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.add_to_cart.*;

import static org.junit.jupiter.api.Assertions.*;

public class AddToCartTest {

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
        User buyer = new User(
                "buyerUser",
                "buyer@mail.com",
                "password123",
                "123 Street"
        );
        product.addScore(5);
        product.addScore(3);
        AddToCartProductDataAccessInterface productDao = new AddToCartProductDataAccessInterface() {
            @Override
            public Product getProduct(String uuid) {
                assertEquals("uuid-123", uuid);
                return product;
            }
        };
        AddToCartUserDataAccessInterface userDao = new AddToCartUserDataAccessInterface() {
            @Override
            public User getUserData(String username) {
                assertEquals("buyerUser", username);
                return buyer;
            }

            @Override
            public void addToCart(User username, String productUUID, Integer quantity) {
                assertEquals(buyer, username);
                assertEquals("uuid-123", productUUID);
                assertEquals(5, quantity);
                username.getCart().addProduct(productDao.getProduct(productUUID),quantity);
            }
        };

            class PresenterStub implements AddToCartOutputBoundary {
                AddToCartOutputData receivedData;
                String recievedError;

                @Override
                public void prepareSuccessView(AddToCartOutputData data) {
                    this.receivedData = data;
                }

                @Override
                public void prepareFailureView(String error) {
                    this.recievedError = error;
                }
            }
            PresenterStub presenter = new PresenterStub();

            AddToCartInteractor interactor =
                    new AddToCartInteractor(userDao, presenter, productDao);

            AddToCartInputData input =
                    new AddToCartInputData("buyerUser", "uuid-123", 5);

            interactor.execute(input);
            assertNotNull(presenter.receivedData);
            assertNull(presenter.recievedError);

            assertEquals("uuid-123", presenter.receivedData.getProductUUID());
            assertEquals("TestProduct", presenter.receivedData.getProductName());
            assertEquals(5*19.99, presenter.receivedData.getCartTotal());
        }

        @Test
        public void testExecuteFailureQuantityZero() {
            AddToCartProductDataAccessInterface productDao = uuid -> null;

            AddToCartUserDataAccessInterface userDao = new AddToCartUserDataAccessInterface() {
                @Override public User getUserData(String username) { return null; }
                @Override public void addToCart(User user, String uuid, Integer q) {}
            };

            class PresenterStub implements AddToCartOutputBoundary {
                AddToCartOutputData successData;
                String errorMsg;

                @Override
                public void prepareSuccessView(AddToCartOutputData data) {
                    this.successData = data;
                }
                @Override
                public void prepareFailureView(String error) {
                    this.errorMsg = error;
                }
            }

            PresenterStub presenter = new PresenterStub();

            AddToCartInteractor interactor =
                    new AddToCartInteractor(userDao, presenter, productDao);

            AddToCartInputData input =
                    new AddToCartInputData("buyerUser", "uuid-123", 0);

            interactor.execute(input);

            assertNull(presenter.successData);
            assertEquals("Quantity should be greater than 0", presenter.errorMsg);
        }}

