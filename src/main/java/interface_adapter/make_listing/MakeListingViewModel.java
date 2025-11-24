package interface_adapter.make_listing;

import interface_adapter.ViewModel;

/**
 * ViewModel for make listing.
 */
public class MakeListingViewModel extends ViewModel<MakeListingState> {

    public static final String VIEW_NAME = "MakeListing";

    public MakeListingViewModel() {
        super(VIEW_NAME);
        setState(new MakeListingState());
    }
}
