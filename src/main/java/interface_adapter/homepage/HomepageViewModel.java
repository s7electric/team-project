package interface_adapter.homepage;

import interface_adapter.ViewModel;

public class HomepageViewModel extends ViewModel<HomepageState> {

    public static final String VIEW_NAME = "homepage";

    public HomepageViewModel() {
        super(VIEW_NAME);
        setState(new HomepageState(""));
    }
}
