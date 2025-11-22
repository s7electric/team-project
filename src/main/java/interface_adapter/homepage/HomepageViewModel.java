package interface_adapter.homepage;

import interface_adapter.ViewModel;

public class HomepageViewModel extends ViewModel<HomepageState> {

    public HomepageViewModel() {
        super("homepage");
        setState(new HomepageState(""));
    }
}