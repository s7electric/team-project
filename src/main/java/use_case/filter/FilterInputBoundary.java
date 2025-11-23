package use_case.filter;

/**
 * This interface is implemented by the filter interactor
 * */
public interface FilterInputBoundary {
    /**
     * Executes the filter use case with the input data
     * @param filterInputData the input data that wraps the user-specified filter category
     * */
    void execute(FilterInputData filterInputData);

    /**
     * Switches to homepage when the state has changed
     * */
    void switchToHomepageView();

    /**
     * Loads all the products when the app boots up
     * */
    void loadProducts();
}
