package use_case.homepage;

public class HomepageOutputData {
    private String productId;
    private String username;
    public HomepageOutputData(String productId, String username) {
        this.productId = productId;
        this.username = username;
    }
    public String getProductId() {
        return productId;
    }
    public String getUsername() {return username;}
}
