package interface_adapter.filter;

import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInputData;

/**
 * This class represents the controller for the filter use case
 * */
public class FilterController {
    private FilterInputBoundary filterInteractor;

    /**
     * Creates a FilterController object
     * @param filterInteractor the interactor for the filter use case
     * */
    public FilterController(FilterInputBoundary filterInteractor){
        this.filterInteractor = filterInteractor;
    }

    /**
     * Executes the filter interactor with the filter category specified by the user
     * @param filter the filter that is specified by the user
     * */
    public void execute(String filter){
        FilterInputData filterInputData = new FilterInputData(filter);
        this.filterInteractor.execute(filterInputData);
    }

    /**
     * Switches to homepage when the state has changed
     * */
    public void switchToHomepageView(){
        this.filterInteractor.switchToHomepageView();
    }

    /**
     * Loads all the products when the app boots up
     * */
    public void loadProducts(){
        this.filterInteractor.loadProducts();
    }
}
