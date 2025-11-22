package use_case.daily_deals;

import java.util.List;

/**
 * Output Data for the Add to Cart Use Case.
 */
class DailyDealsOutputData {
    List<DealItem> items;

    static class DealItem {
        Integer productId;
        String name;
        String imageUrl;
        double displayPrice;
        int discountPercent;
    }
    public DailyDealsOutputData(List<DealItem> items) {
        this.items = items;
    }

    public List<DealItem> getItems() {
        return items;
    }
}