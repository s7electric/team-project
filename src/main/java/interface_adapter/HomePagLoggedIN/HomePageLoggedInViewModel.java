package interface_adapter.HomePagLoggedIN;

import interface_adapter.ViewModel;

/**
 * View model for the logged-in screen.
 */
public class HomePageLoggedInViewModel extends ViewModel<HomePageLoggedInState> {
    public static final String VIEW_NAME = "loggedIn";

    public HomePageLoggedInViewModel() {
        super(VIEW_NAME);
        setState(new HomePageLoggedInState());
    }
}
