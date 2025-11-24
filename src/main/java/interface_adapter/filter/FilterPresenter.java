package interface_adapter.filter;

import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.*;
import use_case.filter.FilterInputData;
import use_case.filter.FilterOutputBoundary;
import use_case.filter.FilterOutputData;

/**
 * This class represents the presenter for the filter use case.
 * This class contains FilterViewModel, FilterState, ViewManagerModel, HomePageViewModel, HomepageState.
 * */
public class FilterPresenter implements FilterOutputBoundary {
    private FilterViewModel filterViewModel;
    private FilterState filterState;
    private ViewManagerModel viewManagerModel;
    private HomepageViewModel homepageViewModel;
    private HomepageState homepageState;

    /**
     * Creates the FilterPresenter object for the filter use case
     * @param filterViewModel the view model for the filter use case
     * @param filterState the state for the filter use case
     * @param viewManagerModel the view model for the view manager
     * @param homepageViewModel the view model for the homepage
     * @param homepageState the state for the homeopage
     * */
    public FilterPresenter(FilterViewModel filterViewModel, FilterState filterState, ViewManagerModel viewManagerModel, HomepageViewModel homepageViewModel, HomepageState homepageState){
        this.filterViewModel = filterViewModel;
        this.filterState = filterState;
        this.viewManagerModel = viewManagerModel;
        this.homepageViewModel = homepageViewModel;
        this.homepageState = homepageState;
    }

    /**
     * Updates the filter state to contain all filter products
     * @param filterOutputData the output data that wraps all the filtered products and the filter category
     * */
    public void updateFilteredProducts(FilterOutputData filterOutputData) {
        this.filterState.setFilterCategory(filterOutputData.getFilterCategory());
        this.filterState.setFilteredProducts(filterOutputData.getFilteredProducts());
        this.filterViewModel.setState(this.filterState);
    }

    /**
     * Switches to homepage when the state has changed
     * */
    public void switchToHomepageView(){
        this.homepageState.setSearchText(this.filterState.getFilterCategory());
        this.homepageState.setProducts(this.filterState.getFilteredProducts());
        this.homepageViewModel.setState(this.homepageState);
        this.viewManagerModel.setActiveViewName(this.homepageViewModel.getViewName());
    }

    /**
     * Loads all the products when the app boots up
     * @param filterOutputData the output data that wraps all the products and the filter category
     * */
    public void loadProducts(FilterOutputData filterOutputData) {
        this.filterState.setFilterCategory(filterOutputData.getFilterCategory());
        this.filterState.setFilteredProducts(filterOutputData.getFilteredProducts());
        this.filterViewModel.setState(this.filterState);
    }
}
