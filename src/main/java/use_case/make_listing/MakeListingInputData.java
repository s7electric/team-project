package use_case.make_listing;

/**
 * The input data from making a listing.
 */
public class MakeListingInputData {
    private String productName;
    private double price;
    private String filePath;
    private String category;
    private String sellerName;

    public MakeListingInputData(String productName, double price, String filePath, String category, String sellerName) {
        this.productName = productName;
        this.price = price;
        this.filePath = filePath;
        this.category = category;
        this.sellerName = sellerName;
    }

    public String getProductName() {
        return productName;
    }
    public double getPrice() {
        return price;
    }
    public String getFilePath() {
        return filePath;
    }
    public String getCategory() {
        return category;
    }
    public String getSellerName() {
        return sellerName;
    }
}