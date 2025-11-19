package interface_adapter.login;

import interface_adapter.ViewModel;

public class LoginViewModel extends ViewModel<LoginState> {
    public static final String VIEW_NAME = "login";
    public static final String TITLE_LABEL = "Login View";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String PASSWORD_LABEL = "Choose password";

    public static final String LOGIN_BUTTON_LABEL = "Login";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String TO_SIGNUP_BUTTON_LABEL = "Sign Up";

    public LoginViewModel() {
        super(VIEW_NAME);
        setState(new LoginState());
    }
}
