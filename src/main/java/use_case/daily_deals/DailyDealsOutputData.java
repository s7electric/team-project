package use_case.daily_deals;

import java.util.List;

/**
 * Output Data for the Add to Cart Use Case.
 */
public class DailyDealsOutputData {

    public final List<DealItem> items;

        public static class DealItem {
            public final Integer productId;
            public final String name;
            public final String imageUrl;
            public final double displayPrice;
            public final int discountPercent;

            public DealItem(Integer productId, String name, String imageUrl, double displayPrice, int discountPercent) {
                this.productId = productId;
                this.name = name;
                this.imageUrl = imageUrl;
                this.displayPrice = displayPrice;
                this.discountPercent = discountPercent;
            }
        }
        public DailyDealsOutputData(List<DealItem> items) {
            this.items = items;
        }

        public List<DealItem> getItems() {
            return items;
        }
    }
