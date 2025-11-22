package use_case.daily_deals;

import entity.Product;

public interface DailyDealsProductDataAccessInterface {

    /**
     * Returns the product associated with a productid
     *
     */
    Product getProductByID(Integer productid);
}
