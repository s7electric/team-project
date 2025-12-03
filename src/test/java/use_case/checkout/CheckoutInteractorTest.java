package use_case.checkout;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutInteractorTest {

    private CheckoutInteractor interactor;
    private TestCheckoutDataAccess testDataAccess;
    private TestCheckoutOutputBoundary testOutputBoundary;
    private User testUser;
    private Cart testCart;
    private Product testProduct;
    private User seller;

    @BeforeEach
    void setUp() {
        // Create test classes
        testDataAccess = new TestCheckoutDataAccess();
        testOutputBoundary = new TestCheckoutOutputBoundary();

        interactor = new CheckoutInteractor(testDataAccess, testOutputBoundary);

        // Create a seller
        seller = new User("seller123", "seller@store.com", "password", "Store Address");

        //Create a Test Product

        testProduct = new Product("Test Product", 100.0, "prod123", "image.jpg", seller, "Electronics");

        //Create a Buyer

        testUser = new User("testuser", "test@example.com", "password", "123 Main St");
        testUser.addBalance(500.0);
        testUser.addPointsBalance(2500);

        testCart = testUser.getCart();
        testDataAccess.addUser(testUser);
    }

    // Test 1: Successful checkout with items in cart, sufficient funds, and points discount
    @Test
    void execute_SuccessfulCheckout_AllPathsCovered() {

        testCart.addProduct(testProduct, 2);

        // Add a default billing address
        Address billingAddress = new Address(
                "Test User",
                "456 Oak St",
                "Apt 5",
                "Test City",
                "TC",
                "12345",
                "Test Country",
                true,
                false
        );
        testUser.addAddress(billingAddress);

        interactor.execute(new CheckoutInputData("testuser"));

        assertTrue(testDataAccess.wasGetUserCalled);
        assertEquals("testuser", testDataAccess.lastUsernameRequested);

        assertFalse(testOutputBoundary.wasCheckoutErrorPresented);
        assertNull(testOutputBoundary.lastErrorMessage);

        // Verify order confirmation was presented
        assertTrue(testOutputBoundary.wasOrderConfirmationPresented);
        assertNotNull(testOutputBoundary.lastOutputData);

        // Verify the output data was created with correct values
        CheckoutOutputData outputData = testOutputBoundary.lastOutputData;
        assertEquals("testuser", outputData.getUsername());
        assertEquals("test@example.com", outputData.getEmail());
        assertTrue(outputData.getBillingAddress().contains("456 Oak St"));


        assertEquals(200.0, outputData.getSubtotal(), 0.001);
        assertEquals(2, outputData.getTotalItems());
        assertEquals(20.0, outputData.getPointsDiscount(), 0.001); // 2500 points = 2 * $10 = $20
        assertEquals(180.0, outputData.getTotalAfterDiscount(), 0.001); // $200 - $20
        assertEquals(500.0, outputData.getUserBalance(), 0.001);
        assertEquals(2500, outputData.getUserPoints());
        assertEquals(180.0, outputData.getAmountFromBalance(), 0.001);
        assertEquals(320.0, outputData.getBalanceAfterPayment(), 0.001); // $500 - $180
    }

    // Test 2: Checkout with empty cart
    @Test
    void execute_EmptyCart_PresentsError() {
        interactor.execute(new CheckoutInputData("testuser"));

        assertTrue(testOutputBoundary.wasCheckoutErrorPresented);
        assertEquals("Cart is empty", testOutputBoundary.lastErrorMessage);
        assertFalse(testOutputBoundary.wasOrderConfirmationPresented);
    }

    // Test 3: Checkout with exception thrown
    @Test
    void execute_DataAccessThrowsException() {

        testDataAccess.shouldThrowException = true;
        testDataAccess.exceptionToThrow = new RuntimeException("Database error");

        interactor.execute(new CheckoutInputData("testuser"));

        assertTrue(testOutputBoundary.wasCheckoutErrorPresented);
        assertEquals("Checkout failed: Database error", testOutputBoundary.lastErrorMessage);
        assertFalse(testOutputBoundary.wasOrderConfirmationPresented);
    }

    // Test 4: Checkout with no billing addresses
    @Test
    void execute_NoBillingAddresses() {
        // Arrange
        testUser.removeAddressById((testUser.getBillingAddresses().get(0)).getId());
        testCart.addProduct(testProduct, 1);
        // No addresses added to user


        interactor.execute(new CheckoutInputData("testuser"));


        assertTrue(testOutputBoundary.wasOrderConfirmationPresented);
        assertEquals("No billing address available",
                testOutputBoundary.lastOutputData.getBillingAddress());
    }


    private static class TestCheckoutDataAccess implements CheckoutDataAccessInterface {
        private final Map<String, User> users = new HashMap<>();
        public boolean wasGetUserCalled = false;
        public String lastUsernameRequested;
        public boolean shouldThrowException = false;
        public RuntimeException exceptionToThrow;

        public void addUser(User user) {
            users.put(user.getUsername(), user);
        }

        @Override
        public User getUser(String username) {
            wasGetUserCalled = true;
            lastUsernameRequested = username;

            if (shouldThrowException && exceptionToThrow != null) {
                throw exceptionToThrow;
            }

            return users.get(username);
        }

        @Override
        public void saveOrder(Order order) {

        }

        @Override
        public void updateUserBalance(String username, double newBalance) {

        }

        @Override
        public void updateUserPoints(String username, int newPoints) {

        }
    }

    private static class TestCheckoutOutputBoundary implements CheckoutOutputBoundary {
        public boolean wasOrderConfirmationPresented = false;
        public CheckoutOutputData lastOutputData;
        public boolean wasCheckoutErrorPresented = false;
        public String lastErrorMessage;

        @Override
        public void presentOrderConfirmation(CheckoutOutputData outputData) {
            wasOrderConfirmationPresented = true;
            lastOutputData = outputData;
            wasCheckoutErrorPresented = false;
            lastErrorMessage = null;
        }

        @Override
        public void presentPaymentScreen() {

        }

        @Override
        public void presentPaymentResult(boolean success, String message) {

        }

        @Override
        public void presentCheckoutError(String errorMessage) {
            wasCheckoutErrorPresented = true;
            lastErrorMessage = errorMessage;
            wasOrderConfirmationPresented = false;
            lastOutputData = null;
        }
    }
}