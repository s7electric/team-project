package interface_adapter.make_listing;

import interface_adapter.ViewModel;

public class MakeListingViewModel extends ViewModel<MakeListingState> {

    private static final String VIEW_NAME = "make_listing";

    public MakeListingViewModel() {
        super(VIEW_NAME);
        setState(new MakeListingState());
    }
    
}
