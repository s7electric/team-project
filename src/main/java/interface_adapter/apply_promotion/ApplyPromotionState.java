package interface_adapter.apply_promotion;

import entity.CartItemDisplay;

import java.util.ArrayList;
import java.util.List;

/**
 * State object for the ApplyPromotionViewModel.
 */
public class ApplyPromotionState {
    private String username;
    private String promoCode;
    private List<CartItemDisplay> items = new ArrayList<>();
    private double subtotal;
    private double discount;
    private double total;
    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public List<CartItemDisplay> getItems() {
        return items;
    }

    public void setItems(List<CartItemDisplay> items) {
        this.items = items;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}