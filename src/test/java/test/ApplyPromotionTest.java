package test;

import entity.Cart;
import entity.Product;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.apply_promotion.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ApplyPromotionTest {

    @Test
    void testEmptyCode() {
        FakeCartDataAccess cartData = new FakeCartDataAccess();
        FakePromotionDataAccess promoData = new FakePromotionDataAccess();
        FakePresenter presenter = new FakePresenter();

        ApplyPromotionInteractor interactor =
                new ApplyPromotionInteractor(cartData, promoData, presenter);

        ApplyPromotionInputData input =
                new ApplyPromotionInputData("alice", "   ");

        interactor.execute(input);

        assertEquals("Promo code cannot be empty.", presenter.lastErrorMessage);
    }

    @Test
    void testCartNotFound() {
        FakeCartDataAccess cartData = new FakeCartDataAccess();
        FakePromotionDataAccess promoData = new FakePromotionDataAccess();
        FakePresenter presenter = new FakePresenter();

        ApplyPromotionInteractor interactor =
                new ApplyPromotionInteractor(cartData, promoData, presenter);

        ApplyPromotionInputData input =
                new ApplyPromotionInputData("bob", "SAVE10");

        interactor.execute(input);

        assertEquals("Cart not found for user: bob", presenter.lastErrorMessage);
    }

    @Test
    void testEmptyCartSubtotal() {
        FakeCartDataAccess cartData = new FakeCartDataAccess();
        FakePromotionDataAccess promoData = new FakePromotionDataAccess();
        FakePresenter presenter = new FakePresenter();

        Cart emptyCart = new Cart("charlie");
        cartData.saveCart("charlie", emptyCart);

        ApplyPromotionInteractor interactor =
                new ApplyPromotionInteractor(cartData, promoData, presenter);

        ApplyPromotionInputData input =
                new ApplyPromotionInputData("charlie", "SAVE10");

        interactor.execute(input);

        assertEquals("Your cart is empty.", presenter.lastErrorMessage);
    }

    @Test
    void testInvalidCode() {
        FakeCartDataAccess cartData = new FakeCartDataAccess();
        FakePromotionDataAccess promoData = new FakePromotionDataAccess();
        FakePresenter presenter = new FakePresenter();

        User seller = new User(
                "seller",
                "seller@example.com",
                999999,
                0.0,
                new HashSet<>(),
                new ArrayList<>(),
                new Cart("seller")
        );

        Cart cart = new Cart("dana");
        Product p = new Product(
                "Laptop",
                1000.0,
                "prod-uuid-1",
                "image.jpg",
                seller,
                "Electronics"
        );
        cart.addProduct(p, 1);
        cartData.saveCart("dana", cart);

        ApplyPromotionInteractor interactor =
                new ApplyPromotionInteractor(cartData, promoData, presenter);

        ApplyPromotionInputData input =
                new ApplyPromotionInputData("dana", "UNKNOWN_CODE");

        interactor.execute(input);

        assertEquals("Invalid or unknown promo code.", presenter.lastErrorMessage);
    }


    private static class FakeCartDataAccess implements CartDataAccessInterface {
        private final Map<String, Cart> carts = new HashMap<>();

        @Override
        public Cart getCartByUsername(String username) {
            return carts.get(username);
        }

        @Override
        public void saveCart(String username, Cart cart) {
            carts.put(username, cart);
        }
    }

    private static class FakePromotionDataAccess implements PromotionDataAccessInterface {
        @Override
        public entity.Promotion findByCode(String code) {
            return null;
        }
    }

    private static class FakePresenter implements ApplyPromotionOutputBoundary {
        ApplyPromotionOutputData lastSuccess;
        String lastErrorMessage;

        @Override
        public void prepareSuccessView(ApplyPromotionOutputData outputData) {
            lastSuccess = outputData;
            lastErrorMessage = null;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            lastSuccess = null;
            lastErrorMessage = errorMessage;
        }
    }
}