package entity;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is an entity representing the shopping cart of Owner.
 * Cart has its owner, and the products inside the cart.
 */
public class Cart {
    private String cartUUID
    private final User owner;
    private final Map<Integer, CartItem> products;

    private String appliedPromotionCode;
    private double promotionDiscountAmount;

    /**
     * Creates a new cart for the owner.
     * @param owner the owner
     */
    public Cart(User owner) {
        this.owner = owner;
        this.products = new HashMap<>();
        this.appliedPromotionCode = null;
        this.promotionDiscountAmount = 0.0;
    }

    public String get CartUUID() {
        return this.cartUUID;
    }
    
    public User getOwner() {
        return owner;
    }

    public Map<Integer, CartItem> getProducts() {
        return Map.copyOf(products);
    }

    public void addProduct(Product product, int quantity) {
        int id = product.getProductid();
        CartItem item = products.get(id);
        if (item == null) {
            products.put(id, new CartItem(product, quantity));
        } else {
            item.increase(quantity);
        }
        // When cart content changes, the promotion should be considered stale.
        clearPromotion();
    }

    public void removeProduct(Product product, int quantity) {
        int id = product.getProductid();
        CartItem item = products.get(id);

        if (item == null) return;

        if (quantity >= item.getQuantity()) {
            products.remove(id);
        } else {
            item.decrease(quantity);
        }
        clearPromotion();
    }

    public int getTotalQuantity() {
        int total = 0;
        for (CartItem item : products.values()) {
            total += item.getQuantity();
        }
        return total;
    }

    /**
     * Returns the subtotal of the cart (sum of all item subtotals, before promotions).
     */
    public double getSubtotal() {
        double subtotal = 0.0;
        for (CartItem item : products.values()) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    /**
     * Clears any applied promotion.
     */
    public void clearPromotion() {
        this.appliedPromotionCode = null;
        this.promotionDiscountAmount = 0.0;
    }

    /**
     * Applies a promotion with the given code and absolute discount amount.
     * Caller is responsible for validating the discount amount.
     */
    public void applyPromotion(String promoCode, double discountAmount) {
        this.appliedPromotionCode = promoCode;
        this.promotionDiscountAmount = discountAmount;
    }

    /**
     * Returns the currently applied promotion code, or null if none.
     */
    public String getAppliedPromotionCode() {
        return appliedPromotionCode;
    }

    /**
     * Returns the currently applied promotion discount amount in dollars.
     */
    public double getPromotionDiscountAmount() {
        return promotionDiscountAmount;
    }

    /**
     * Returns the total after applying the promotion discount, never less than 0.
     */
    public double getTotalAfterDiscount() {
        double total = getSubtotal() - promotionDiscountAmount;
        return Math.max(0.0, total);
    }
}