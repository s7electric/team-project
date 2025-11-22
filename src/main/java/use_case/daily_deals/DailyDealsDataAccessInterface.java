package use_case.daily_deals;

import java.util.List;

public interface DailyDealsDataAccessInterface {
    /**
     * Returns a list of productids and their respective discounts
     */
    List<DealInfo> getDailyDeals();
}
