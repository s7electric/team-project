package entity;

public class Product {

    private String name;
    private double price;
    private String imageUrl;
    private User seller;


    public Product(String name, double price, String imageUrl, User seller) {
        if (name.isEmpty()) {throw new IllegalArgumentException("Product name cannot be empty");}
        this.name = name;
        if (price < 0) {throw new IllegalArgumentException("Product price cannot be negative");}
        this.price = price;
        if (imageUrl.isEmpty()) {throw new IllegalArgumentException("Product image url cannot be empty");}
        this.imageUrl = imageUrl;
        this.seller = seller;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public User getUser() {
        return seller;
    }

}
