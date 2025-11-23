package entity;

public class CartItemDisplay {
    private final String productName;
    private final double price;
    private final int quantity;
    private final double subtotal;

    public CartItemDisplay(String productName, double price, int quantity, double subtotal) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    // Getters
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return subtotal; }
}
