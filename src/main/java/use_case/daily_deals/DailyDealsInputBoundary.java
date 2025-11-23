package use_case.daily_deals;

/**
 * Input Boundary for actions which are related to adding items to cart.
 */
public interface DailyDealsInputBoundary {
    /**
     * Executes the daily deals use case.
     * After this executes, the user will be on a daily deals view.
     */
    void execute();
}
