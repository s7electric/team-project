package interface_adapter.Product;

import entity.Product;

/**
 * This class represents the state of the product use case
 * This class contains the product to be displayed
 * */
public class ProductState {
    private String name;
    private String price; // format: "$12.50"
    private String imageUrl;
    private String sellerName;
    private String category;
    private String rating; // format: "4.7 *"
    private String reviewCount; // format: "23 reviews"

    public String getName(){return name;}
    public void setName(String name) {this.name = name;}
    public String getPrice(){return price;}
    public void setPrice(String price){this.price = price;}
    public String getImageUrl(){return imageUrl;}
    public void setImageUrl(String imageUrl){this.imageUrl = imageUrl;}
    public String getSellerName(){return sellerName;}
    public void setSellerName(String sellerName){this.sellerName = sellerName;}
    public String getCategory(){return category;}
    public void setCategory(String category){this.category = category;}
    public String getRating(){return rating;}
    public void setRating(String rating){this.rating = rating;}
    public String getReviewCount(){return reviewCount;}
    public void setReviewCount(String reviewCount){this.reviewCount = reviewCount;}
}
