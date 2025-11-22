package interface_adapter.filter;

import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageViewModel;
import use_case.filter.FilterOutputBoundary;
import use_case.filter.FilterOutputData;

public class FilterPresenter implements FilterOutputBoundary {
    private FilterViewModel filterViewModel;
    private FilterState filterState;
    private ViewManagerModel viewManagerModel;
    private HomepageViewModel homepageViewModel
    public FilterPresenter(FilterViewModel filterViewModel, FilterState filterState, ViewManagerModel viewManagerModel){
        this.filterViewModel = filterViewModel;
        this.filterState = filterState;
        this.viewManagerModel = viewManagerModel;
    }
    public void updateFilteredProducts(FilterOutputData filterOutputData) {
        this.filterState.setFilteredProducts(filterOutputData.getFilteredProducts());
        this.filterViewModel.setState(this.filterState);
    }
    public void switchToHomepageView(){
        this.viewManagerModel.setActiveViewName(this.homepageViewModel.getViewName());
    }
}
