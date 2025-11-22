package use_case.daily_deals;

import entity.Product;

import java.util.List;

public interface DailyDealsProductDataAccessInterface {

    /**
     * Returns a list of daily deals.
     */
    List<Product> getDailyDealsProducts();
}
