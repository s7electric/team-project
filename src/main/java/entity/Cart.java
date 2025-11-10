package entity;

public class Cart {

    private final User owner;
    private final List<Product> products;

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
