package interface_adapter.filter;

import interface_adapter.ViewModel;

public class FilterViewModel extends ViewModel<FilterState> {
    public FilterViewModel(){
        super("Filter");
        setState(new FilterState());
    }
}
