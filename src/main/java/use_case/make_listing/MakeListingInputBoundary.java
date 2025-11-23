package use_case.make_listing;

/**
 * The input boundary for the Make Listing use case.
 */
public interface MakeListingInputBoundary {
    /**
     * Executes the Make Listing use case.
     * @param makeListingInputData The input data for making a listing.
     */
    void execute(MakeListingInputData makeListingInputData);

    /*