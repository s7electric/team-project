package use_case.open_product;

import entity.User;

/**
 * Output Data for the Add to Cart Use Case.
 */
public class OpenProductOutputData {

    private String name;
    private double price;
    private String productUUID;
    private String imageUrl;
    private User seller;
    private String category;
    private double averageReviewScore;
    private int totalReviews;

    public OpenProductOutputData(String username, double price, String productid, String imageUrl, User seller, String category, double averageReviewScore, int totalReviews) {
        this.name = username;
        this.price = price;
        this.productUUID = productid;
        this.imageUrl = imageUrl;
        this.seller = seller;
        this.category = category;
        this.averageReviewScore = averageReviewScore;
        this.totalReviews = totalReviews;

    }

    public String getUsername() {return name;}
    public Double getPrice() {return price;}
    public String getProductUUID() {return productUUID;}
    public String getImageURl() {return imageUrl;}
    public User getSeller() {return seller;}
    public String getCategory() {return category;}
    public double getAverageReviewScore() {return averageReviewScore;}
    public int getTotalReviews() {return totalReviews;}

}
