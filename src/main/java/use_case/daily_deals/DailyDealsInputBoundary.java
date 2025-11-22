package use_case.daily_deals;

/**
 * Input Boundary for actions which are related to adding items to cart.
 */
public interface DailyDealsInputBoundary {
    /**
     * Executes the add to cart iuse case.
     * @param dailyDealsInputData the input data
     */
    void execute(DailyDealsInputData dailyDealsInputData);
}
