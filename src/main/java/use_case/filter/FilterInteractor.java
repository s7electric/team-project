package use_case.filter;

public class FilterInteractor implements FilterInputBoundary{
    private FilterOutputBoundary filterPresenter;
    private FilterDataAccessInterface dataAccess;

    public FilterInteractor(FilterOutputBoundary filterPresenter, FilterDataAccessInterface dataAccess){
        this.filterPresenter = filterPresenter;
        this.dataAccess = dataAccess;
    }

    public void addFilter(FilterInputData filterInputData){

    }
}
