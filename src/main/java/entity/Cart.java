package entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class is an entity representing the shopping cart of Owner.
 * Cart has its owner, and the products inside the cart.
 */
public class Cart {
    private String cartUUID;
    private final String ownerName;
    // private final Map<Integer, CartItem> products;
    private final Map<String, CartItem> products;

    private String appliedPromotionCode;
    private double promotionDiscountAmount;

    /**
     * Creates a new cart for the owner.
     * @param owner the owner
     */
    public Cart(String ownerName) {
        this.ownerName = ownerName;
        this.products = new HashMap<>();
        this.cartUUID = UUID.randomUUID().toString();
        this.appliedPromotionCode = null;
        this.promotionDiscountAmount = 0.0;
    }

    /* This method is not for creating new carts, only for formatting DB data. */
    public Cart(String ownerName, String cartUUID) {
        this.ownerName = ownerName;
        this.products = new HashMap<>();
        this.cartUUID = cartUUID;
    }

    public String getCartUUID() {
        return this.cartUUID;
    }
    
    public String getOwnerName() {
        return ownerName;
    }

    // public Map<Integer, CartItem> getProducts() {
    public Map<String, CartItem> getProducts() {
        return Map.copyOf(products); // makes products unmodifiable
    }

    // public void addProduct(Product product, int quantity) {
    //     int id = product.getProductid();
    //     CartItem item = products.get(id);
    //     if (item == null)
    //         products.put(id, new CartItem(product, quantity));
    //     else
    //         products.get(id).increase(quantity);
    //     }
    public void addProduct(Product product, int quantity) {
        String id = product.getProductUUID();
        CartItem item = products.get(id);
        if (item == null) {
            products.put(id, new CartItem(product, quantity));
        } else {
            item.increase(quantity);
        }
        // When cart content changes, the promotion should be considered stale.
        clearPromotion();
    }

    // public void removeProduct(Product product, int quantity) {
    //     int id = product.getProductid();
    //     CartItem item = products.get(id);

    //     if (item == null) return;

    //     if (quantity >= item.getQuantity()) {
    //         products.remove(id); // if the quantity removed is larger than the current number in cart
    //     } else {
    //         item.decrease(quantity); // if the quantity in the cart is larger than the number removed
    //     }
    // }
    public void removeProduct(Product product, int quantity) {
        String id = product.getProductUUID();
        CartItem item = products.get(id);

        if (item == null) return;

        if (quantity >= item.getQuantity()) {
            products.remove(id); // if the quantity removed is larger than the current number in cart
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