package use_case.make_listing;

public interface MakeListingOutputBoundary {

    /**
     * Prepares a success view after valid listing is created
     * @param outputData relevant output data
     */
    void prepareSuccessView(MakeListingOutputData outputData);

    /**
     * Prepares a failure view (in case of invalid user input, for example)
     * @param errorMessage user will be shown the error message
     */
    void prepareFailView(MakeListingOutputData outputData);

    /**
     * Return to home page after everything is finished
     */
    void switchToHomePageView();
}