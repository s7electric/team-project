package use_case.apply_promotion;

import entity.CartItemDisplay;

import java.util.List;

/**
 * Output data for the Apply Promotion use case.
 */
public class ApplyPromotionOutputData {
    private final String username;
    private final String promoCode;
    private final List<CartItemDisplay> items;
    private final double subtotal;
    private final double discount;
    private final double total;
    private final String message;

    public ApplyPromotionOutputData(String username,
                                    String promoCode,
                                    List<CartItemDisplay> items,
                                    double subtotal,
                                    double discount,
                                    double total,
                                    String message) {
        this.username = username;
        this.promoCode = promoCode;
        this.items = items;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public List<CartItemDisplay> getItems() {
        return items;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return total;
    }

    public String getMessage() {
        return message;
    }
}