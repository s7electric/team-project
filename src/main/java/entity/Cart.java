package entity;

/**
 * This class is an entity representing the shopping cart of Owner.
 * Cart has its owner, and the products inside the cart
 * */
public class Cart {

    private final User owner;
    private final List<Product> products;

    /**
     * Creates a new cart for the owner.
     * @param owner the owner
     */
    public Cart(User owner) {
        this.owner = owner;
        this.products = new ArrayList<>();
    }

    public User getOwner() {
        return owner;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public int getTotalQuantity() {
        return products.size();
    }
}
