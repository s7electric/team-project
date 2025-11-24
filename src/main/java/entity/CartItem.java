package entity;

public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increase(int amount) {
        quantity += amount;
    }

    public void decrease(int amount) {
        quantity -= amount;
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
}