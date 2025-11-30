package interface_adapter.make_listing;

import interface_adapter.ViewManagerModel;
import use_case.make_listing.MakeListingOutputBoundary;
import use_case.make_listing.MakeListingOutputData;

/**
 * Presenter for the make listing use case. Updates view models and changes views.
 */
public class MakeListingPresenter implements MakeListingOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final MakeListingViewModel makeListingViewModel;

    public MakeListingPresenter(ViewManagerModel viewManagerModel,
                                MakeListingViewModel makeListingViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.makeListingViewModel = makeListingViewModel;
    }

    @Override
    public void prepareSuccessView(MakeListingOutputData outputData) {
        MakeListingState state = new MakeListingState(outputData.getUsername());
        makeListingViewModel.setState(state);
        viewManagerModel.setActiveViewName(makeListingViewModel.getViewName());
    }
}