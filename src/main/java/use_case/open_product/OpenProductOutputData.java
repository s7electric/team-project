package use_case.open_product;

/**
 * Output Data for the Add to Cart Use Case.
 */
public class OpenProductOutputData {

    private String name;
    private double price;
    private String productId;
    private String imageUrl;
    private String seller;
    private String category;
    private double averageReviewScore;
    private int totalReviews;
    private String username;

    public OpenProductOutputData(String name, double price, String productid, String imageUrl, String seller,
                                 String category, double averageReviewScore, int totalReviews, String username) {
        this.name = name;
        this.price = price;
        this.productId = productid;
        this.imageUrl = imageUrl;
        this.seller = seller;
        this.category = category;
        this.averageReviewScore = averageReviewScore;
        this.totalReviews = totalReviews;
        this.username = username;

    }

    public String getProductName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getProductId() {
        return productId;
    }

    public String getImageURl() {
        return imageUrl;
    }

    public String getSeller() {
        return seller;
    }

    public String getCategory() {
        return category;
    }

    public double getAverageReviewScore() {
        return averageReviewScore;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public String getUsername() {
        return username;
    }

}
