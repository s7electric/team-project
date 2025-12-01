package test;

import entity.Cart;
import entity.CartItem;
import entity.Product;
import entity.User;
import use_case.apply_promotion.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ApplyPromotionTest {

    public static void main(String[] args) {

        System.out.println("=== ApplyPromotionInteractor Manual Tests (Final Version) ===");

        testEmptyPromoCode();
        line();

        testCartNotFound();
        line();

        testEmptyCart();
        line();

        testInvalidPromoCode();
        line();

        System.out.println("All manual tests finished.");
    }

    private static void line() {
        System.out.println("--------------------------------------");
    }

    private static void testEmptyPromoCode() {
        System.out.println("[Test] Empty promo code");

        FakeCartDataAccess cartDB = new FakeCartDataAccess();
        FakePromotionDataAccess promoDB = new FakePromotionDataAccess();
        FakePresenter presenter = new FakePresenter();

        ApplyPromotionInteractor interactor =
                new ApplyPromotionInteractor(cartDB, promoDB, presenter);

        ApplyPromotionInputData input =
                new ApplyPromotionInputData("alice", "   ");

        interactor.execute(input);

        System.out.println("Expected: Promo code cannot be empty.");
        System.out.println("Actual:   " + presenter.lastError);
    }


    private static void testCartNotFound() {
        System.out.println("[Test] Cart not found");

        FakeCartDataAccess cartDB = new FakeCartDataAccess();
        FakePromotionDataAccess promoDB = new FakePromotionDataAccess();
        FakePresenter presenter = new FakePresenter();

        ApplyPromotionInteractor interactor =
                new ApplyPromotionInteractor(cartDB, promoDB, presenter);

        ApplyPromotionInputData input =
                new ApplyPromotionInputData("bob", "SAVE10");

        interactor.execute(input);

        System.out.println("Expected: Cart not found for user: bob");
        System.out.println("Actual:   " + presenter.lastError);
    }


    private static void testEmptyCart() {
        System.out.println("[Test] Empty cart subtotal");

        FakeCartDataAccess cartDB = new FakeCartDataAccess();
        FakePromotionDataAccess promoDB = new FakePromotionDataAccess();
        FakePresenter presenter = new FakePresenter();

        Cart emptyCart = new Cart("charlie");
        cartDB.saveCart("charlie", emptyCart);

        ApplyPromotionInteractor interactor =
                new ApplyPromotionInteractor(cartDB, promoDB, presenter);

        ApplyPromotionInputData input =
                new ApplyPromotionInputData("charlie", "SAVE10");

        interactor.execute(input);

        System.out.println("Expected: Your cart is empty.");
        System.out.println("Actual:   " + presenter.lastError);
    }


    private static void testInvalidPromoCode() {
        System.out.println("[Test] Invalid promo code");

        FakeCartDataAccess cartDB = new FakeCartDataAccess();
        FakePromotionDataAccess promoDB = new FakePromotionDataAccess();
        FakePresenter presenter = new FakePresenter();

        Cart cart = new Cart("dana");

        Product laptop = new Product(
                "Laptop",
                1000.0,
                UUID.randomUUID().toString(),
                "laptop.png",
                new User("seller1", "s@example.com", "pwd", "somewhere"),
                "electronics"
        );

        cart.addProduct(laptop, 1);

        cartDB.saveCart("dana", cart);

        ApplyPromotionInteractor interactor =
                new ApplyPromotionInteractor(cartDB, promoDB, presenter);

        ApplyPromotionInputData input =
                new ApplyPromotionInputData("dana", "NOT_REAL");

        interactor.execute(input);

        System.out.println("Expected: Invalid or unknown promo code.");
        System.out.println("Actual:   " + presenter.lastError);
    }

    private static class FakeCartDataAccess implements CartDataAccessInterface {
        Map<String, Cart> store = new HashMap<>();

        @Override
        public Cart getCartByUsername(String username) {
            return store.get(username);
        }

        @Override
        public void saveCart(String username, Cart cart) {
            store.put(username, cart);
        }
    }

    private static class FakePromotionDataAccess implements PromotionDataAccessInterface {
        @Override
        public entity.Promotion findByCode(String code) {
            return null;
        }
    }

    private static class FakePresenter implements ApplyPromotionOutputBoundary {

        String lastError = null;
        ApplyPromotionOutputData lastSuccess = null;

        @Override
        public void prepareSuccessView(ApplyPromotionOutputData outputData) {
            lastError = null;
            lastSuccess = outputData;

            System.out.println("[Presenter] SUCCESS");
            System.out.println("  Username: " + outputData.getUsername());
            System.out.println("  Code    : " + outputData.getPromoCode());
            System.out.println("  Total   : " + outputData.getTotal());
        }

        @Override
        public void prepareFailView(String errorMessage) {
            lastSuccess = null;
            lastError = errorMessage;

            System.out.println("[Presenter] FAIL: " + errorMessage);
        }
    }
}