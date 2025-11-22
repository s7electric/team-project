package use_case.daily_deals;

/**
 * The output boundary for the Daily Deals Use Case.
 */
public interface DailyDealsOutputBoundary {
    /**
     * Prepares the success view for the Daily Deals Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(DailyDealsOutputData outputData);
}
