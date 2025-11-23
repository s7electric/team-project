package interface_adapter.filter;

import interface_adapter.ViewModel;

/**
 * This class represents the view model for the filter use case.
 * */
public class FilterViewModel extends ViewModel<FilterState> {
    /**
     * Creates a FilterViewModel object to update the filter view
     * */
    public FilterViewModel(){
        super("Filter");
        setState(new FilterState());
    }
}
