package entity;

public class Order {
    private final User customer;
    private final Product[] products;
    private final String address;
    private final double price;


    public Order(User customer, Product[] products, String address, double price) {
        this.customer = customer;
        this.products = products;
        this.address = address;
        this.price = price;
    }

    public User getCustomer() {
        return customer;
    }

    public Product[] getProducts() {
        return products;
    }

    public String getAddress() {
        return address;
    }

    public double getPrice() {
        return price;
    }
}
