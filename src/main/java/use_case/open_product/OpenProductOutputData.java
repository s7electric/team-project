package use_case.open_product;

import entity.User;

import java.util.ArrayList;

/**
 * Output Data for the Add to Cart Use Case.
 */
public class OpenProductOutputData {

    private String name;
    private double price;
    private Integer productid;
    private String imageUrl;
    private User seller;
    private String category;
    private double averageReviewScore;

    public OpenProductOutputData(String username, double price, Integer productid, String imageUrl, User seller, String category, double averageReviewScore) {
        this.name = username;
        this.price = price;
        this.productid = productid;
        this.imageUrl = imageUrl;
        this.seller = seller;
        this.category = category;
        this.averageReviewScore = averageReviewScore;
    }

    public String getUsername() {return name;}
    public Double getPrice() {return price;}
    public Integer getProductid() {return productid;}
    public String getImageURl() {return imageUrl;}
    public User getSeller() {return seller;}
    public String getCategory() {return category;}
    public double getAverageReviewScore() {return averageReviewScore;}

}
