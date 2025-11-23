package use_case.daily_deals;

// the class returned by the Data Access interface

public class DealInfo {
    private final Integer productid;
    private final int discountPercentage;
    DealInfo(Integer productid, int discountPercentage) {
        this.productid = productid;
        this.discountPercentage = discountPercentage;
    }
    public Integer getProductid() {
        return productid;
    }
    public int getDiscountPercentage() {
        return discountPercentage;
    }
}
