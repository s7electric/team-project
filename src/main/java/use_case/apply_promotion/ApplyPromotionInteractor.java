package use_case.apply_promotion;

import entity.Cart;
import entity.CartItem;
import entity.CartItemDisplay;
import entity.Promotion;
import entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Interactor for Apply promotions.
 */
public class ApplyPromotionInteractor implements ApplyPromotionInputBoundary {

    private final CartDataAccessInterface cartDataAccess;
    private final PromotionDataAccessInterface promotionDataAccess;
    private final ApplyPromotionOutputBoundary presenter;

    public ApplyPromotionInteractor(CartDataAccessInterface cartDataAccess,
                                    PromotionDataAccessInterface promotionDataAccess,
                                    ApplyPromotionOutputBoundary presenter) {
        this.cartDataAccess = cartDataAccess;
        this.promotionDataAccess = promotionDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ApplyPromotionInputData inputData) {
        String username = inputData.getUsername();
        String code = inputData.getPromoCode();
        if (code == null || code.trim().isEmpty()) {
            presenter.prepareFailView("Promo code cannot be empty.");
            return;
        }
        code = code.trim().toUpperCase();

        Cart cart = cartDataAccess.getCartByUsername(username);
        if (cart == null) {
            presenter.prepareFailView("Cart not found for user: " + username);
            return;
        }

        double subtotal = cart.getSubtotal();
        if (subtotal <= 0.0) {
            presenter.prepareFailView("Your cart is empty.");
            return;
        }

        Promotion promotion = promotionDataAccess.findByCode(code);
        if (promotion == null) {
            presenter.prepareFailView("Invalid or unknown promo code.");
            return;
        }

        if (!promotion.isActive()) {
            presenter.prepareFailView("This promo code is inactive or expired.");
            return;
        }

        double discount = promotion.calculateDiscount(subtotal);
        if (discount <= 0.0) {
            presenter.prepareFailView("Conditions for this code are not met.");
            return;
        }

        cart.applyPromotion(code, discount);
        cartDataAccess.saveCart(username, cart);

        List<CartItemDisplay> displayItems = buildDisplayItems(cart);

        double total = cart.getTotalAfterDiscount();
        String message = "Promotion applied: " + code;

        ApplyPromotionOutputData outputData = new ApplyPromotionOutputData(
                username,
                code,
                displayItems,
                subtotal,
                discount,
                total,
                message
        );
        presenter.prepareSuccessView(outputData);
    }

    /**
     * Converts CartItems into CartItemDisplay objects for the UI.
     */
    private List<CartItemDisplay> buildDisplayItems(Cart cart) {
        List<CartItemDisplay> result = new ArrayList<>();
        for (Map.Entry<Integer, CartItem> entry : cart.getProducts().entrySet()) {
            CartItem item = entry.getValue();
            Product product = item.getProduct();
            String name = product.getName();
            double price = product.getPrice();
            int qty = item.getQuantity();
            double subtotal = item.getSubtotal();
            result.add(new CartItemDisplay(name, price, qty, subtotal));
        }
        return result;
    }
}