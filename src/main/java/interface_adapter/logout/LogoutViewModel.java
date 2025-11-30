package interface_adapter.logout;

import interface_adapter.ViewModel;

public class LogoutViewModel extends ViewModel<LogoutState> {
    public static final String VIEW_NAME = "logout";
    public static final String TITLE_LABEL = "Logout View";
    public static final String LOGOUT_BUTTON_LABEL = "Logout";

    public LogoutViewModel() {
        super(VIEW_NAME);
        setState(new LogoutState());
    }
}
