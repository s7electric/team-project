package entity;
import java.util.*;


/**
 * This class is an entity representing the shopping cart of Owner.
 * Cart has its owner, and the products inside the cart
 * */
public class Cart {
    private String cartUUID
    private final User owner;
    private final Map<Integer, CartItem> products;

    /**
     * Creates a new cart for the owner.
     * @param owner the owner
     */
    public Cart(User owner) {
        this.owner = owner;
        this.products = new HashMap<>();
    }

    public String get CartUUID() {
        return this.cartUUID;
    }
    
    public User getOwner() {
        return owner;
    }

    public Map<Integer, CartItem> getProducts() {
        return Map.copyOf(products); // makes products unmodifiable
    }

    public void addProduct(Product product, int quantity) {
        int id = product.getProductid();
        CartItem item = products.get(id);
        if (item == null)
            products.put(id, new CartItem(product, quantity));
        else
            products.get(id).increase(quantity);
        }

    public void removeProduct(Product product, int quantity) {
        int id = product.getProductid();
        CartItem item = products.get(id);

        if (item == null) return;

        if (quantity >= item.getQuantity()) {
            products.remove(id); // if the quantity removed is larger than the current number in cart
        } else {
            item.decrease(quantity); // if the quantity in the cart is larger than the number removed
        }
    }

    public int getTotalQuantity() {
        int total = 0;
        for (CartItem item : products.values()) {
            total += item.getQuantity();
        }
        return total;
    }
}
