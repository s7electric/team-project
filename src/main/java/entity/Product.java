package entity;

import java.util.ArrayList;

public class Product {

    private String name;
    private double price;
    private String productUUID;
    private String imageUrl;
    private User seller;
    private String category;
    private double averageReviewScore;
    private ArrayList<Integer> scores;


    public Product(String name, double price, String productUUID, String imageUrl, User seller, String category) {
        if (name.isEmpty()) {throw new IllegalArgumentException("Product name cannot be empty");}
        this.name = name;
        if (price < 0) {throw new IllegalArgumentException("Product price cannot be negative");}
        this.price = price;
        if  (productUUID == null) {throw new IllegalArgumentException("Product productUUID cannot be null");}
        this.productUUID = productUUID;
        if (imageUrl.isEmpty()) {throw new IllegalArgumentException("Product image url cannot be empty");}
        this.imageUrl = imageUrl;
        this.seller = seller;
        if (category.isEmpty()) {throw new IllegalArgumentException("Product category cannot be empty");}
        this.category = category;
        this.averageReviewScore = 0;
        this.scores = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        if (price < 0 ) { throw new IllegalArgumentException("Product price cannot be negative");}
        this.price = price;
    }
    public Integer getProductUUID() {
        return productUUID;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public User getUser() {
        return seller;
    }
    public String getCategory() {
        return category;
    }
    public double getAverageReviewScore() {
        return averageReviewScore;
    }
    public ArrayList<Integer> getScores() {
        return scores;
    }
    public void addScore(Integer score) {
        if (score < 1 || score > 5) {throw new IllegalArgumentException("Invalid score value");}
        scores.add(score);
        double newTotal = 0.0   ;
        for (Integer integer : scores) {
            newTotal += integer;
        }
        averageReviewScore = newTotal / scores.size();
    }
}
