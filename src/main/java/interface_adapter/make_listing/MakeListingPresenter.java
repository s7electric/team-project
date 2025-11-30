package interface_adapter.make_listing;

import javax.swing.JOptionPane;

import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import use_case.make_listing.MakeListingOutputBoundary;
import use_case.make_listing.MakeListingOutputData;

/**
 * Presenter for the make listing use case. Updates view models and changes views.
 */
public class MakeListingPresenter implements MakeListingOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final MakeListingViewModel makeListingViewModel;
    private final HomepageViewModel homepageViewModel;

    public MakeListingPresenter(ViewManagerModel viewManagerModel,
                                MakeListingViewModel makeListingViewModel,
                                HomepageViewModel homepageViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.makeListingViewModel = makeListingViewModel;
        this.homepageViewModel = homepageViewModel;
    }

    @Override
    public void prepareSuccessView(MakeListingOutputData outputData) {
        HomepageState current = homepageViewModel.getState();
        HomepageState next = new HomepageState(outputData.getUsername());
        next.setSearchText(current.getSearchText());
        next.setProducts(current.getProducts());
        homepageViewModel.setState(next);

        makeListingViewModel.setState(new MakeListingState());
        viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
        JOptionPane.showMessageDialog(null, outputData.getMessage());
    }

    @Override
    public void prepareFailView(MakeListingOutputData outputData) {
        HomepageState current = homepageViewModel.getState();
        HomepageState next = new HomepageState(outputData.getUsername());
        next.setSearchText(current.getSearchText());
        next.setProducts(current.getProducts());
        homepageViewModel.setState(next);

        makeListingViewModel.setState(new MakeListingState());
        viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
        JOptionPane.showMessageDialog(null, outputData.getMessage());
    }

    @Override
    public void switchToHomePageView() {
        viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
    }
}